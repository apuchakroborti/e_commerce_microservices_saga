package com.apu.order.services;


import com.apu.order.dto.CustomerOrderDto;
import com.apu.order.dto.request.OrderSearchCriteria;
import com.apu.order.exceptions.GenericException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    CustomerOrderDto placeOrder(CustomerOrderDto orderDto)throws GenericException;
    Page<CustomerOrderDto> getAllOrderInfoBySearchCriteria(OrderSearchCriteria criteria, Pageable pageable) throws GenericException;
}
