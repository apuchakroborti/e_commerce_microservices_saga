package com.apu.product.entity;

import com.apu.product.utils.ImageType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "IMAGES")
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrls {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String url;
    private ImageType imageType;
}
