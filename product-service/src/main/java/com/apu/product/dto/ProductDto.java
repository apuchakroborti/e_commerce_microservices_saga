package com.apu.product.dto;

import com.apu.product.entity.ImageUrls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends CommonDto {
    private Long id;

    private String productCode;

    private String barCode;
    private String qrCode;

    private Integer price;
    private Double discountPercentage;

    private List<ImageUrls> imageUrlsList = new ArrayList<>();

}
