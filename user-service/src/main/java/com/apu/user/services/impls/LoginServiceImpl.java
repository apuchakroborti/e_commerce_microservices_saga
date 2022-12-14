package com.apu.user.services.impls;

import com.apu.user.dto.CustomUserDto;
import com.apu.user.dto.request.LoginRequestDto;
import com.apu.user.entity.Customer;
import com.apu.user.exceptions.GenericException;
import com.apu.user.repository.CustomUserRepository;
import com.apu.user.security_oauth2.repository.UserRepository;
import com.apu.user.services.LoginService;
import com.apu.user.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier("userPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomUserDto checkLoginUser(LoginRequestDto loginRequestDto) throws GenericException {
        try {
            CustomUserDto employeeDto = new CustomUserDto();

            Optional<com.apu.user.security_oauth2.models.security.User> optionalUser = userRepository.findByUsername(loginRequestDto.getUsername());
            if (optionalUser.isPresent()) {
                if (passwordEncoder.matches(loginRequestDto.getPassword(), optionalUser.get().getPassword())) {
                    Optional<Customer> optionalEmployee = customUserRepository.findByEmail(loginRequestDto.getUsername());
                    if (optionalEmployee.isPresent()) {
                        Utils.copyProperty(optionalEmployee.get(), employeeDto);
                    } else {
                        employeeDto.setFirstName("Admin");
                        employeeDto.setLastName("Admin");
                    }
                } else {
                    throw new GenericException("Username or password is incorrect!");
                }
            } else {
                throw new GenericException("Username or password is incorrect!");
            }
            return employeeDto;
        }catch (Exception e){
            logger.error("Exception occurred while checking login user, message: {}", e.getMessage());
            throw new GenericException(e.getMessage(), e);
        }
    }
}