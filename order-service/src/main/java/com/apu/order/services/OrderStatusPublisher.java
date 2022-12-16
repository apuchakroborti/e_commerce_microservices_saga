package com.apu.order.services;

import com.apu.commons.dto.order.CustomerOrderDto;
import com.apu.commons.event.order.OrderEvent;
import com.apu.commons.event.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;


    public void publishOrderEvent(CustomerOrderDto customerOrderRequestDto, OrderStatus orderStatus){
        log.info("Publishing event from order service: order id: {}, customer id: {}, order status: {}",
                customerOrderRequestDto.getId(),
                customerOrderRequestDto.getCustomerId(),
                orderStatus);
        OrderEvent orderEvent = new OrderEvent(customerOrderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
