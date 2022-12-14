package com.apu.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonDto implements Serializable {
    private Long createdBy;
    private LocalDateTime createTime;
    private Long editedBy;
    private LocalDateTime editTime;
}
