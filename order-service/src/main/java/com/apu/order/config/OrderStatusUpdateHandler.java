package com.apu.order.config;

import com.apu.commons.dto.order.CustomerOrderDto;
import com.apu.commons.event.order.OrderStatus;
import com.apu.commons.event.payment.PaymentStatus;
import com.apu.order.entity.CustomerOrder;
import com.apu.order.repository.OrderRepository;
import com.apu.order.services.OrderStatusPublisher;
import com.apu.order.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(Long id, Consumer<CustomerOrder> consumer) {
        repository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(CustomerOrder purchaseOrder) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isPaymentComplete) {
            publisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
        }
    }

    public CustomerOrderDto convertEntityToDto(CustomerOrder customerOrder) {
        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        Utils.copyProperty(customerOrder, customerOrderDto);

        return customerOrderDto;

       /* OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;*/
    }
}
