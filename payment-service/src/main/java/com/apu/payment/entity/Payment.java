package com.apu.payment.entity;

import com.apu.payment.utils.PaymentMedium;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

//using @Getter and @Setter instead of @Data to remove remove recursive toString and EqualsAndHashCode method
@Entity
@Getter
@Setter
@Table(name = "PAYMENTS")
public class Payment extends EntityCommon{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    private Double due;
    private String comments;

    @Column(name = "PAYMENT_MEDIUM", nullable = false)
    private PaymentMedium paymentMedium;

    @Column(name = "TRANSACTION_ID", nullable = false)
    private String transactionId;

    @Column(name = "PAYMENT_DATE", nullable = false)
    private LocalDate paymentDate;

    private String status;
}
