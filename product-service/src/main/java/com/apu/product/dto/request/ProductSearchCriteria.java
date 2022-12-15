package com.apu.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCriteria {
    private Long productId;
    private String productCode;
    private String barCode;
    private String qrCode;
    private Double productPriceRangeHigh;
    private Double productPriceRangeLow;
    private Double discountPercentageRangeLow;
    private Double discountPercentageRangeHigh;
    private LocalDate fromDate;
    private LocalDate toDate;
}
