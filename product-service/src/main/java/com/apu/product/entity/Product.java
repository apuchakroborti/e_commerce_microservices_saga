package com.apu.product.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "PRODUCTS")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends EntityCommon {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    private Boolean status;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ImageUrls> imageUrlsList = new ArrayList<>();
}
