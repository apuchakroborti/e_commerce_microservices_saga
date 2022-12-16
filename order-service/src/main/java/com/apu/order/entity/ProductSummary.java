package com.apu.order.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT_SUMMARY")
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummary {

    @Id
    private Long id;

    @Column(name = "PRODUCT_PRICE", nullable = false)
    private Double productPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;
}
