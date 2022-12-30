package com.apu.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto extends CommonDto{
    private Long id;

    @NotBlank(message = "User Id should not be empty or null!")
    private String userId;

    @NotBlank(message = "First Name should not be empty or null!")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email should not be empty or null!")
    private String email;
    private String phone;
    private String nid;

    @NotNull(message = "Date of Joining should not be null!")
    private LocalDate dateOfBirth;
    private Boolean status;
    private Boolean walletStatus;

    private List<AddressDto> addressList;
}
