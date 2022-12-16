package com.apu.order.services.impls;

import com.apu.commons.dto.order.CustomerOrderDto;
import com.apu.commons.dto.product.ProductSummaryDto;
import com.apu.commons.event.order.OrderStatus;
import com.apu.order.dto.request.OrderSearchCriteria;
import com.apu.order.entity.CustomerOrder;
import com.apu.order.entity.ProductSummary;
import com.apu.order.exceptions.GenericException;
import com.apu.order.repository.OrderRepository;
import com.apu.order.services.OrderService;
import com.apu.order.services.OrderStatusPublisher;
import com.apu.order.specifications.OrderSearchSpecifications;
import com.apu.order.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusPublisher orderStatusPublisher;

    @Autowired
    OrderServiceImpl(OrderRepository orderRepository,
                     OrderStatusPublisher orderStatusPublisher){
        this.orderRepository = orderRepository;
        this.orderStatusPublisher = orderStatusPublisher;
    }


    @Override
    @Transactional
    public CustomerOrderDto placeOrder(CustomerOrderDto customerOrderDto) throws GenericException {
        try {
            log.info("OrderServiceImpl::placeOrder service start: customer Id: {}",customerOrderDto.getCustomerId());

            //TODO check the products are available or not
            CustomerOrder purchaseOrder = new CustomerOrder();
            Utils.copyProperty(customerOrderDto, purchaseOrder);

            purchaseOrder.setOrderPlacingDate(LocalDate.now());
            purchaseOrder.setExpectedDeliveryDate(LocalDate.now().plus(7, ChronoUnit.DAYS));

            //summation of all products price
            Double totalAmount = 0.0;
            List<ProductSummary> productSummaryList = new ArrayList<>();
            for(ProductSummaryDto productSummaryDto: customerOrderDto.getProductList()){
                ProductSummary summary = new ProductSummary();
                Utils.copyProperty(productSummaryDto, summary);
                productSummaryList.add(summary);

                totalAmount+=productSummaryDto.getProductPrice();
            }
            purchaseOrder.setProductSummaryList(productSummaryList);
            purchaseOrder.setTotalAmount(totalAmount);

            purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
            purchaseOrder.setStatus(true);
            purchaseOrder.setCreatedBy(1L);
            purchaseOrder.setCreateTime(LocalDateTime.now());

            purchaseOrder = orderRepository.save(purchaseOrder);

            Utils.copyProperty(purchaseOrder, customerOrderDto);
            log.info("OrderServiceImpl::placeOrder service end: customer Id: {}",customerOrderDto.getCustomerId());

            //produce kafka event with status ORDER_CREATED
            orderStatusPublisher.publishOrderEvent(customerOrderDto, OrderStatus.ORDER_CREATED);

            return customerOrderDto;
        }catch (Exception e){
            log.error("Error occurred while placing order, customer Id: {}", customerOrderDto.getCustomerId());
            throw new GenericException("Internal server error", e);
        }
    }

    @Override
    public Page<CustomerOrderDto> getAllOrderInfoBySearchCriteria(OrderSearchCriteria criteria, Pageable pageable) throws GenericException{
        try{
            log.info("OrderServiceImpl::getAllOrderInfoByUserId service start");
            Page<CustomerOrderDto> customerOrderDtoPage = orderRepository.findAll(
                    OrderSearchSpecifications.withId(criteria.getId())
                    .and(OrderSearchSpecifications.withCustomerId(criteria.getCustomerId()))
                    .and(OrderSearchSpecifications.withOrderPlacingDate(criteria.getOrderPlacingDate()))
                    .and(OrderSearchSpecifications.withExpectedDeliveryDate(criteria.getExpectedDeliveryDate()))
                    , pageable
            ).map(new Function<CustomerOrder, CustomerOrderDto>() {
                @Override
                public CustomerOrderDto apply(CustomerOrder entity) {
                    CustomerOrderDto dto = new CustomerOrderDto();
                    Utils.copyProperty(entity, dto);
                    return dto;
                }
            });
            log.info("OrderServiceImpl::getAllOrderInfoByUserId service end");
            return customerOrderDtoPage;
        }catch (Exception e){
            log.error("OrderServiceImpl::getAllOrderInfoByUserId: Exception occurred while getAllOrderBySearchCriteria");
            throw new GenericException("Exception occurred while Exception occurred while getAllOrderBySearchCriteria !");
        }

    }

}
