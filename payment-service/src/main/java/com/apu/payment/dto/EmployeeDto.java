package com.apu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto extends CommonDto implements Serializable {
    private Long id;

    @NotBlank(message = "User Id should not be empty or null!")
    private String userId;

    @NotBlank(message = "First Name should not be empty or null!")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email should not be empty or null!")
    private String email;
    private String phone;
    private String tin;
    private String nid;
    private String passport;

    @NotNull(message = "Date of Joining should not be null!")
    private LocalDate dateOfJoining;
    private Integer designationId;
    private Integer addressId;
    private Boolean status;

    @NotBlank(message = "Password should not be empty or null!")
    private String password;

    @NotBlank(message = "Password should not be empty or null!")
    private String confirmPassword;

    @NotNull(message = "Gross salary must not be null!")
    private Double grossSalary;
}
