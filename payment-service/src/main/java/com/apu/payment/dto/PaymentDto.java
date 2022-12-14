package com.apu.payment.dto;

import com.apu.payment.entity.EntityCommon;
import com.apu.payment.utils.PaymentMedium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto extends EntityCommon {
    private Long id;
    private Long orderId;
    private Double amount;
    private Double due;
    private String status;
    private String comments;
    private LocalDate paymentDate;
    private PaymentMedium paymentMedium;
    private String transactionId;
}
