package com.apu.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchCriteria {
    private Long id;
    private Long customerId;
    private LocalDate orderPlacingDate;
    private LocalDate expectedDeliveryDate;
}
