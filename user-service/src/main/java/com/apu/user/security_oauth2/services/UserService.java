package com.apu.user.security_oauth2.services;


import com.apu.user.dto.request.PasswordChangeRequestDto;
import com.apu.user.dto.request.PasswordResetRequestDto;
import com.apu.user.dto.response.PasswordChangeResponseDto;
import com.apu.user.exceptions.GenericException;

public interface UserService {
    PasswordChangeResponseDto changeUserPassword(PasswordChangeRequestDto passwordChangeRequestDto) throws GenericException;
    PasswordChangeResponseDto resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws GenericException;
}