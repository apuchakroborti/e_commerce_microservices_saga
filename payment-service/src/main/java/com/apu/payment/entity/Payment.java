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
public class Payment{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    private Double due;

    private String status;

    private String comments;

    @Column(name = "PAYMENT_DATE", nullable = false)
    private LocalDate paymentDate;

    private PaymentMedium paymentMedium;
    private String transactionId;
}
