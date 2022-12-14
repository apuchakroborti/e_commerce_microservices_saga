package com.apu.order.controllers;

import com.apu.order.dto.OrderDto;
import com.apu.order.dto.response.APIResponse;
import com.apu.order.dto.response.Pagination;
import com.apu.order.entity.Order;
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
    public ResponseEntity<APIResponse> placeOrder(@Valid @RequestBody OrderDto taxDepositDto) throws GenericException {
        log.info("OrderController::placeOrder request body {}", Utils.jsonAsString(taxDepositDto));

        OrderDto employeeResponseDTO = orderService.placeOrder(taxDepositDto);
        log.debug("OrderController::insertTaxInfo response {}", Utils.jsonAsString(employeeResponseDTO));

        //Builder Design pattern
        APIResponse<OrderDto> responseDTO = APIResponse
                .<OrderDto>builder()
                .status("SUCCESS")
                .results(employeeResponseDTO)
                .build();

        log.info("EmployeeController::placeOrder response {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getAllOrderDetailsByUserId(@PathVariable("userId") Long userId, @PageableDefault(value = 12) Pageable pageable) throws GenericException{
        log.info("OrderController::getAllOrderDetailsByUserId requested employee id: {}", userId);

        Page<Order> taxDepositPage = orderService.getAllOrderInfoByUserId(userId, pageable);
        List<OrderDto> employeeTaxDepositDtoList = Utils.toDtoList(taxDepositPage, OrderDto.class);

        APIResponse<List<OrderDto>> responseDTO = APIResponse
                .<List<OrderDto>>builder()
                .status("SUCCESS")
                .results(employeeTaxDepositDtoList)
                .pagination(new Pagination(taxDepositPage.getTotalElements(), taxDepositPage.getNumberOfElements(), taxDepositPage.getNumber(), taxDepositPage.getSize()))
                .build();

        log.info("OrderController::getAllOrderDetailsByUserId response: {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
