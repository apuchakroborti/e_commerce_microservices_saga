package com.apu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityModel {
    private Long id;
    @NotNull(message = "name must not be null!")
    @NotEmpty(message = "name must not be empty!")
    private String name;
}
