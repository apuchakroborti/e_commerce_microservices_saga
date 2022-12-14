package com.apu.payment.services;


import com.apu.payment.dto.PaymentDto;
import com.apu.payment.dto.request.PaymentSearchCriteria;
import com.apu.payment.entity.Payment;
import com.apu.payment.exceptions.GenericException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
   PaymentDto doPayment(PaymentDto paymentDto) throws GenericException;
   Page<Payment> getPaymentInfoBySearchCriteria(PaymentSearchCriteria criteria, Pageable pageable) throws GenericException;
}
