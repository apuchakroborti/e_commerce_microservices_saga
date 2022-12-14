package com.apu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvidentFundDto extends CommonDto {
    private Long id;

    @NotNull(message = "employee id should not be null!")
    private Long employeeId;

    @NotNull(message = "employee gross salary should not be null!")
    private Double grossSalary;
    private Double employeeContribution;
    private Double companyContribution;
    private String comments;

    @NotNull(message = "Month value should not be null!")
    private LocalDate month;

    private LocalDate fromDate;
    private LocalDate toDate;
}
