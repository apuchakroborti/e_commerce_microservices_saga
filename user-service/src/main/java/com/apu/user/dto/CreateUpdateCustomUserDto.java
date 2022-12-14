package com.apu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateCustomUserDto implements Serializable {
    private Long id;

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

    @NotBlank(message = "Password should not be empty or null!")
    private String password;

    @NotBlank(message = "Password should not be empty or null!")
    private String confirmPassword;

    @NotNull(message = "Address must be given")
    @Size(min = 1, max = 3, message = "Minimum one and maximum 3 addresses should be inserted")
    private List<AddressDto> addressDtoList;

}
