package com.apu.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotEmpty(message = "username mandatory")
    @NotNull(message = "username should not be null!")
    private String username;

    @NotEmpty(message = "password mandatory")
    @NotNull(message = "password should not be null!")
    private String password;
}
