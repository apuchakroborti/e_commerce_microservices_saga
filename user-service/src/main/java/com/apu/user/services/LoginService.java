package com.apu.user.services;


import com.apu.user.dto.CustomerDto;
import com.apu.user.dto.request.LoginRequestDto;
import com.apu.user.exceptions.GenericException;

public interface LoginService {
    CustomerDto checkLoginUser(LoginRequestDto loginRequestDto) throws GenericException;

}
