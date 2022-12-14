package com.apu.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonDto {
    private Long createdBy;
    private LocalDateTime createTime;
    private Long editedBy;
    private LocalDateTime editTime;
}
