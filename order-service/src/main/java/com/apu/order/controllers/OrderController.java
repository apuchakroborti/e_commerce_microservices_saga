package com.apu.order.controllers;

import com.apu.commons.dto.APIResponse;
import com.apu.commons.dto.Pagination;
import com.apu.commons.dto.order.CustomerOrderDto;
import com.apu.order.dto.request.OrderSearchCriteria;
import com.apu.order.entity.CustomerOrder;
import com.apu.order.exceptions.GenericException;
import com.apu.order.services.OrderService;
import com.apu.order.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<APIResponse> placeOrder(@Valid @RequestBody CustomerOrderDto customerOrderDto) throws GenericException {
        log.info("OrderController::placeOrder request body {}", Utils.jsonAsString(customerOrderDto));

        CustomerOrderDto employeeResponseDTO = orderService.placeOrder(customerOrderDto);
        log.debug("OrderController::insertTaxInfo response {}", Utils.jsonAsString(employeeResponseDTO));

        //Builder Design pattern
        APIResponse<CustomerOrderDto> responseDTO = APIResponse
                .<CustomerOrderDto>builder()
                .status("SUCCESS")
                .results(employeeResponseDTO)
                .build();

        log.info("EmployeeController::placeOrder response {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<APIResponse> getAllOrderDetailsByUserId(OrderSearchCriteria criteria, @PageableDefault(value = 12) Pageable pageable) throws GenericException{
        log.info("OrderController::getAllOrderDetailsByUserId requested ");

        Page<CustomerOrderDto> customerOrderDtos = orderService.getAllOrderInfoBySearchCriteria(criteria, pageable);

        APIResponse<List<CustomerOrderDto>> responseDTO = APIResponse
                .<List<CustomerOrderDto>>builder()
                .status("SUCCESS")
                .results(customerOrderDtos.getContent())
                .pagination(new Pagination(customerOrderDtos.getTotalElements(), customerOrderDtos.getNumberOfElements(), customerOrderDtos.getNumber(), customerOrderDtos.getSize()))
                .build();

        log.info("OrderController::getAllOrderDetailsByUserId response: {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
