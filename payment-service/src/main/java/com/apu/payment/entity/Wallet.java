package com.apu.payment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "WALLETS")
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
}
