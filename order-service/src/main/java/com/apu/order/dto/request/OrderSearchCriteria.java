package com.apu.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchCriteria {
    private Long customerId;
    private LocalDate fromDate;
    private LocalDate toDate;
}
