package com.apu.payment.controllers;

import com.apu.commons.dto.APIResponse;
import com.apu.commons.dto.Pagination;
import com.apu.payment.dto.PaymentDto;
import com.apu.payment.dto.request.PaymentSearchCriteria;
import com.apu.payment.entity.Payment;
import com.apu.payment.exceptions.GenericException;
import com.apu.payment.services.PaymentService;
import com.apu.payment.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> doPayment(@Valid @RequestBody PaymentDto requestDto) throws GenericException {
        log.info("PaymentController::doPayment request: {}", Utils.jsonAsString(requestDto));
        PaymentDto monthlyPaySlipDto = paymentService.doPayment(requestDto);
        APIResponse<PaymentDto> responseDTO = APIResponse
                .<PaymentDto>builder()
                .status("SUCCESS")
                .results(monthlyPaySlipDto)
                .build();

        log.info("PaymentController::doPayment response {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getPaymentListBySearchCriteria(PaymentSearchCriteria criteria, @PageableDefault(value = 12) Pageable pageable) throws GenericException{
        log.info("PaymentController::getPaymentListBySearchCriteria: criteria: {}", Utils.jsonAsString(criteria));

        Page<Payment> payslipPage = paymentService.getPaymentInfoBySearchCriteria(criteria, pageable);
        List<PaymentDto> employeeDtoList = Utils.toDtoList(payslipPage, PaymentDto.class);
        APIResponse<List<PaymentDto>> responseDTO = APIResponse
                .<List<PaymentDto>>builder()
                .status("SUCCESS")
                .results(employeeDtoList)
                .pagination(new Pagination(payslipPage.getTotalElements(), payslipPage.getNumberOfElements(), payslipPage.getNumber(), payslipPage.getSize()))
                .build();

        log.info("PaymentController::getPaymentListBySearchCriteria: response: {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
