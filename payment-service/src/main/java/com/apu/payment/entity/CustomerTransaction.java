package com.apu.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMERS_TRANSACTIONS")
@NoArgsConstructor
@Getter
@Setter
public class CustomerTransaction {
    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDate transactionTime;

    public CustomerTransaction(Long orderId, Long customerId, Double amount){
        this.orderId = orderId;
        this.customerId =customerId;
        this.amount=amount;
        this.transactionTime = LocalDate.now();
    }
}
