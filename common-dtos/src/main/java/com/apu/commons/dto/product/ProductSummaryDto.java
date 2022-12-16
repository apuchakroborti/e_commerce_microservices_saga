package com.apu.commons.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryDto {
    @NotNull(message = "Product id must be provided!")
    private Long id;
    private Double productPrice;
}
