package com.apu.commons.dto.product;

import com.apu.commons.event.product.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrlsDto {
    private Long id;
    private String imageUrl;
    private ImageType imageType;
}
