package com.apu.user.controllers;

import com.apu.user.dto.CustomUserDto;
import com.apu.user.dto.request.LoginRequestDto;
import com.apu.user.exceptions.GenericException;
import com.apu.user.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    /*
    * this API is for checking the user is valid by username and password
    * @response: employee details
    * */
    @PostMapping
    public CustomUserDto checkLoginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) throws GenericException {
        return loginService.checkLoginUser(loginRequestDto);
    }
}
