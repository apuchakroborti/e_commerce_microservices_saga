package com.apu.user.security_oauth2.services;


import com.apu.user.dto.request.PasswordChangeRequestDto;
import com.apu.user.dto.request.PasswordResetRequestDto;
import com.apu.user.dto.response.PasswordChangeResponseDto;
import com.apu.user.exceptions.EmployeeNotFoundException;
import com.apu.user.exceptions.GenericException;
import com.apu.user.entity.Customer;
import com.apu.user.repository.CustomerRepository;
import com.apu.user.security_oauth2.repository.AuthorityRepository;
import com.apu.user.security_oauth2.repository.UserRepository;
import com.apu.user.utils.Defs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailService implements UserService, UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserDetailService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

   @Autowired
   @Qualifier("userPasswordEncoder")
   private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        logger.info("loadUserByUsername called");
        Optional<com.apu.user.security_oauth2.models.security.User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isPresent()){
            if(!optionalUser.get().isEnabled()){
                throw new UsernameNotFoundException(Defs.USER_INACTIVE+": "+username);
            }
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(Defs.USER_NOT_FOUND+": "+username);
    }

    @Override
    public PasswordChangeResponseDto changeUserPassword(PasswordChangeRequestDto requestDto) throws GenericException {
        try {
            com.apu.user.security_oauth2.models.security.User currentUser = (com.apu.user.security_oauth2.models.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currentUsername = currentUser.getUsername();

            UserDetails userDetails = loadUserByUsername(currentUsername);
            String currentPassword = userDetails.getPassword();
            com.apu.user.security_oauth2.models.security.User user = (com.apu.user.security_oauth2.models.security.User) userDetails;

            if (passwordEncoder.matches(requestDto.getCurrentPassword(), currentPassword)) {
                user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
                userRepository.save(user);
            } else {
                throw new GenericException(Defs.PASSWORD_MISMATCHED);
            }

            return new PasswordChangeResponseDto(true, Defs.PASSWORD_CHANGED_SUCCESSFUL);
        }catch (Exception e){
            logger.error("Error occurred while updating password");
            throw new GenericException("Error occurred while updating password", e);
        }
    }

    @Override
    public PasswordChangeResponseDto resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws GenericException{

        try {
            Optional<Customer> optionalEmployee = customerRepository.findByEmail(passwordResetRequestDto.getUsername());
            if (!optionalEmployee.isPresent() || optionalEmployee.get().getStatus().equals(false)) {
                throw new EmployeeNotFoundException(Defs.USER_NOT_FOUND);
            }

            UserDetails userDetails = loadUserByUsername(passwordResetRequestDto.getUsername());
            com.apu.user.security_oauth2.models.security.User user = (com.apu.user.security_oauth2.models.security.User) userDetails;

            user.setPassword(passwordEncoder.encode("apu12345"));
            userRepository.save(user);

            return new PasswordChangeResponseDto(true,
                            Defs.PASSWORD_CHANGED_SUCCESSFUL + ": the new Password is: apu12345");
        }catch (Exception e){
            logger.error("Error occurred while resetting password");
            throw new GenericException("Error occurred while resetting password", e);
        }
    }
}