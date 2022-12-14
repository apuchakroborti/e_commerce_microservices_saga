package com.apu.user.services;


import com.apu.user.dto.CustomUserDto;
import com.apu.user.dto.request.LoginRequestDto;
import com.apu.user.exceptions.GenericException;

public interface LoginService {
    CustomUserDto checkLoginUser(LoginRequestDto loginRequestDto) throws GenericException;

}
