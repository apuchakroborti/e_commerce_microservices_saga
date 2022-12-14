package com.apu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalaryDto extends CommonDto {
    private Long id;

    @NotNull(message = "employee id can't be null!")
    private EmployeeDto employee;

    @NotNull(message = "Gross salary can't be null!")
    private Double grossSalary;
    private Double basicSalary;

    private String comments;

    @NotNull(message = "from date must be provided")
    private LocalDate fromDate;
    private LocalDate toDate;

    private String status;
}
