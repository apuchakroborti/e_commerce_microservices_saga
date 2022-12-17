package com.apu.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "WALLETS")
@NoArgsConstructor
public class Wallet extends EntityCommon{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "status", nullable = false)
    private Boolean status;

    //this is temporary
    public Wallet(Long customerId, Double balance, Boolean status){
        this.customerId = customerId;
        this.balance = balance;
        this.status=status;
        this.setCreatedBy(1L);
        this.setCreateTime(LocalDateTime.now());
    }

}
