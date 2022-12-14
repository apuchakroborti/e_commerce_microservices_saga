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

    @Column(name = "product_code")
    private String productCode;

    private String barCode;
    private String qrCode;

    private Integer price;
    private Double discountPercentage;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ImageUrls> imageUrlsList = new ArrayList<>();
}
