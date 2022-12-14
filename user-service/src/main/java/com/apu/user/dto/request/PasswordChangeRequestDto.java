package com.apu.user.dto.request;

import lombok.Data;

@Data
public class PasswordChangeRequestDto {

    private String currentPassword;

    private String newPassword;
}