package com.apu.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyPaySlipJoiningRequestDto {
    @NotNull(message = "Employee Id should not be null!")
    private Long employeeId;

    @NotNull(message = "Gross salary should not be null!")
    private Double grossSalary;

    @NotNull(message = "Joining date should not be null!")
    private LocalDate joiningDate;
}
