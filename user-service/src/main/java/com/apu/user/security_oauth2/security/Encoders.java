package com.apu.user.security_oauth2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Encoders {

    @Bean(name = "oauthClientPasswordEncoder")
    public PasswordEncoder oauthClientPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean(name = "userPasswordEncoder")
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
