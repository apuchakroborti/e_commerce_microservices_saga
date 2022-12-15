package com.apu.user.services;

import com.apu.user.dto.CreateUpdateCustomUserDto;
import com.apu.user.dto.CustomUserDto;
import com.apu.user.dto.request.CustomUserSearchCriteria;
import com.apu.user.entity.Customer;
import com.apu.user.exceptions.GenericException;
import com.apu.user.security_oauth2.models.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUserService {
    CustomUserDto signUpUser(CreateUpdateCustomUserDto customUserDto) throws GenericException;
    User addOauthUser(Customer employee, String password) throws GenericException;

    CustomUserDto findByUsername(String username) throws GenericException;
    CustomUserDto findCustomerById(Long id) throws GenericException;
    CustomUserDto updateCustomerById(Long id, CustomUserDto employeeBean) throws GenericException;
    Page<Customer> getCustomerList(CustomUserSearchCriteria criteria, Pageable pageable) throws GenericException;
    Boolean deleteCustomerById(Long id) throws GenericException;
}
