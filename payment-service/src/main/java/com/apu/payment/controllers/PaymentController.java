package com.apu.payment.controllers;

import com.apu.payment.dto.APIResponse;
import com.apu.payment.dto.PaymentDto;
import com.apu.payment.dto.Pagination;
import com.apu.payment.dto.request.MonthlyPaySlipJoiningRequestDto;
import com.apu.payment.dto.request.MonthlyPaySlipRequestDto;
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
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<APIResponse> doPayment(@Valid @RequestBody MonthlyPaySlipRequestDto requestDto) throws GenericException {
        log.info("PaySlipController::generatePaySlip request: {}", Utils.jsonAsString(requestDto));
        PaymentDto monthlyPaySlipDto = paymentService.doPayment(requestDto);
        APIResponse<PaymentDto> responseDTO = APIResponse
                .<PaymentDto>builder()
                .status("SUCCESS")
                .results(monthlyPaySlipDto)
                .build();

        log.info("PaySlipController::generatePaySlip response {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    @PostMapping("/join")
    public ResponseEntity<APIResponse> generatePaySlipWhileJoining(@Valid @RequestBody MonthlyPaySlipJoiningRequestDto requestDto) throws GenericException {
        log.info("PaySlipController::generatePaySlip request: {}", Utils.jsonAsString(requestDto));
        paymentService.generatePayslipForCurrentFinancialYear(
                requestDto.getEmployeeId(),
                requestDto.getGrossSalary(),
                requestDto.getJoiningDate());

        APIResponse<Boolean> responseDTO = APIResponse
                .<Boolean>builder()
                .status("SUCCESS")
                .results(true)
                .build();

        log.info("PaySlipController::generatePaySlip response {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity<APIResponse>  getPaySlipBySearchCriteria(PaymentSearchCriteria criteria, @PageableDefault(value = 12) Pageable pageable) throws GenericException{
        log.info("PaySlipController::getPaySlipBySearchCriteria: criteria: {}", Utils.jsonAsString(criteria));

        Page<Payment> payslipPage = paymentService.getPaymentInfoBySearchCriteria(criteria, pageable);
        List<PaymentDto> employeeDtoList = Utils.toDtoList(payslipPage, PaymentDto.class);
        APIResponse<List<PaymentDto>> responseDTO = APIResponse
                .<List<PaymentDto>>builder()
                .status("SUCCESS")
                .results(employeeDtoList)
                .pagination(new Pagination(payslipPage.getTotalElements(), payslipPage.getNumberOfElements(), payslipPage.getNumber(), payslipPage.getSize()))
                .build();

        log.info("PaySlipController::getPaySlipBySearchCriteria: response: {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
