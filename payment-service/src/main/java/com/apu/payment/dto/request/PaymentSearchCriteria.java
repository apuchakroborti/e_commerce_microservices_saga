package com.apu.payment.dto.request;

import com.apu.payment.utils.PaymentMedium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSearchCriteria {
    private Long id;
    private Long orderId;
    private Long customerId;
    private PaymentMedium paymentMedium;
    private String transactionId;
    private Boolean status;
    private LocalDate fromDate;
    private LocalDate toDate;
}
