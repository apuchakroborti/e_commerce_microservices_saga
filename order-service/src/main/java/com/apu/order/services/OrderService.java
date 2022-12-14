package com.apu.order.services;


import com.apu.order.dto.OrderDto;
import com.apu.order.entity.Order;
import com.apu.order.exceptions.GenericException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    OrderDto placeOrder(OrderDto employeeTaxDepositDto)throws GenericException;
    Page<Order> getAllOrderInfoByUserId(Long userId, Pageable pageable) throws GenericException;
}
