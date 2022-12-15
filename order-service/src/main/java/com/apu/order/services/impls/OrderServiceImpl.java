package com.apu.order.services.impls;

import com.apu.order.dto.CustomerOrderDto;
import com.apu.order.dto.request.OrderSearchCriteria;
import com.apu.order.entity.CustomerOrder;
import com.apu.order.exceptions.GenericException;
import com.apu.order.repository.OrderRepository;
import com.apu.order.services.OrderService;
import com.apu.order.specifications.OrderSearchSpecifications;
import com.apu.order.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    @Override
    public CustomerOrderDto placeOrder(CustomerOrderDto orderDto) throws GenericException{
        try {
            log.info("OrderServiceImpl::placeOrder service start: employee Id: {}",orderDto.getCustomUserId());


            //TODO check the products are available or not
            CustomerOrder order = new CustomerOrder();
            Utils.copyProperty(orderDto, order);

            order.setCreatedBy(1L);
            order.setCreateTime(LocalDateTime.now());

            order = orderRepository.save(order);

            log.debug("OrderServiceImpl::placeOrder: employeeTaxDeposity info: {}", Utils.jsonAsString(order));

            Utils.copyProperty(order, orderDto);
            log.info("OrderServiceImpl::placeOrder service end: employee Id: {}",orderDto.getCustomUserId());

            return orderDto;
        }catch (Exception e){
            log.error("Error occurred while inserting individual tax info, employeeId: {}", orderDto.getCustomUserId());
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
