package com.apu.user.services;

import com.apu.user.dto.AuthorityModel;
import com.apu.user.exceptions.GenericException;


public interface AuthorityService {
    AuthorityModel addNewAuthority(AuthorityModel authorityModel) throws GenericException;
}
