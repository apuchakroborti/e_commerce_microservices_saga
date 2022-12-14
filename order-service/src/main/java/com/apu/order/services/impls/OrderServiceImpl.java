package com.apu.order.services.impls;

import com.apu.order.dto.OrderDto;
import com.apu.order.entity.Order;
import com.apu.order.exceptions.GenericException;
import com.apu.order.repository.OrderRepository;
import com.apu.order.services.OrderService;
import com.apu.order.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    @Override
    public OrderDto placeOrder(OrderDto orderDto) throws GenericException{
        try {
            log.info("OrderServiceImpl::placeOrder service start: employee Id: {}",orderDto.getCustomUserId());


            Order order = new Order();
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
    public Page<Order> getAllOrderInfoByUserId(Long userId, Pageable pageable) throws GenericException{
        try{
            log.info("OrderServiceImpl::getAllOrderInfoByUserId service start: employee Id: {}", userId);

            log.info("OrderServiceImpl::getAllOrderInfoByUserId service end: employee Id: {}", userId);
            return null;
        }catch (Exception e){
            log.error("OrderServiceImpl::getAllOrderInfoByUserId: Exception occurred while getAllTaxInfoByEmployeeId: employee Id: {}", userId);
            throw new GenericException("Exception occurred while getAllTaxInfoByEmployeeId !");
        }

    }

}
