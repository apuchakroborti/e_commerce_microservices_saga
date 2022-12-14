package com.apu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTaxDepositDto extends CommonDto {
    private Long id;
    @NotNull(message = "Employee Id should not be null!")
    private Long employeeId;
    private Double amount;
    private String chalanNo;
    private String comments;

    @NotNull(message = "From date should not be null!")
    private LocalDate fromDate;
    @NotNull(message = "To date should not be null!")
    private LocalDate toDate;
}
