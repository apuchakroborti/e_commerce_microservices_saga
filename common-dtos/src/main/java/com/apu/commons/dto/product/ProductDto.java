package com.apu.commons.dto.product;

import com.apu.commons.dto.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends CommonDto {
    private Long id;

    @NotNull(message = "Product Name must not be null!")
    @NotEmpty(message = "Product Name must not be empty!")
    private String productName;

    @NotNull(message = "Product Code must not be null!")
    @NotEmpty(message = "Product Code must not be empty!")
    private String productCode;
    private String barCode;
    private String qrCode;

    @NotNull(message = "Product Price must not be null!")
    private Double productPrice;
    private Double discountPercentage;
    private Boolean status;
    private List<ImageUrlsDto> imageUrlsList = new ArrayList<>();

}
