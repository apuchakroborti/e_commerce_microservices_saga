package com.apu.user.entity;

import com.apu.user.utils.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "address_type", columnDefinition ="ENUM('PRESENT_ADDRESS', 'PERMANENT_ADDRESS', 'BILLING_ADDRESS')" , nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(name = "details")
    private String addressDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;
}
