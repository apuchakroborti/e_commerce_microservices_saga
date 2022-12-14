package com.apu.user.security_oauth2.security;


import com.apu.user.exceptions.GenericException;
import com.apu.user.dto.CustomUserDto;
import com.apu.user.security_oauth2.models.security.Authority;
import com.apu.user.security_oauth2.models.security.User;
import com.apu.user.services.CustomUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CustomTokenEnhancer implements TokenEnhancer {

    Logger logger = LoggerFactory.getLogger(CustomTokenEnhancer.class);

    @Autowired
    private CustomUserService employeeService;


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        logger.info("CustomTokenEnhancer enhance called");
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        CustomUserDto loginInfoDto = null;
        try {
            loginInfoDto = employeeService.findByUsername(user.getUsername());
        } catch (GenericException e) {
            e.printStackTrace();
        }
        if(loginInfoDto != null) {
            additionalInfo.put("id", loginInfoDto.getId());
            additionalInfo.put("userId", loginInfoDto.getUserId());
        }

        additionalInfo.put("authorities", user.getAuthorities()
                .stream().map(Authority::getAuthority)
                .collect(Collectors.toList()));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}