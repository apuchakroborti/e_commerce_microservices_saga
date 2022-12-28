package com.apu.user.controllers;

import com.apu.user.dto.*;
import com.apu.user.dto.request.CustomUserSearchCriteria;
import com.apu.user.dto.request.PasswordChangeRequestDto;
import com.apu.user.dto.request.PasswordResetRequestDto;
import com.apu.user.dto.response.PasswordChangeResponseDto;
import com.apu.user.entity.Customer;
import com.apu.user.exceptions.GenericException;
import com.apu.user.security_oauth2.services.UserService;
import com.apu.user.services.CustomerService;
import com.apu.user.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final UserService userService;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<APIResponse> signUpUser(@Valid @RequestBody CreateUpdateCustomUserDto customUserDto) throws GenericException {
        log.info("CustomerController::signUpUser request body {}", Utils.jsonAsString(customUserDto));

        CustomerDto employeeResponseDTO = customerService.signUpUser(customUserDto);
        log.debug(CustomerController.class.getName()+"::signUpUser response {}", Utils.jsonAsString(employeeResponseDTO));

        //Builder Design pattern
        APIResponse<CustomerDto> responseDTO = APIResponse
                .<CustomerDto>builder()
                .status("SUCCESS")
                .results(employeeResponseDTO)
                .build();

        log.info("CustomerController::signUpUser response {}", Utils.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<APIResponse> searchEmployee(CustomUserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException {
        log.info("CustomerController::searchEmployee start...");

        Page<Customer>  employeePage = customerService.getCustomerList(criteria, pageable);

        List<CustomerDto> employeeDtoList = Utils.toDtoList(employeePage, CustomerDto.class);

        APIResponse<List<CustomerDto>> responseDTO = APIResponse
                .<List<CustomerDto>>builder()
                .status("SUCCESS")
                .results(employeeDtoList)
                .pagination(new Pagination(employeePage.getTotalElements(), employeePage.getNumberOfElements(), employeePage.getNumber(), employeePage.getSize()))
                .build();

        log.info("CustomerController::searchEmployee end");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<APIResponse> getEmployeeById(@PathVariable(name = "id") Long id ) throws GenericException {
        log.info("CustomerController::getEmployeeById start...");

        CustomerDto employeeDto = customerService.findCustomerById(id);

        APIResponse<CustomerDto> responseDTO = APIResponse
                .<CustomerDto>builder()
                .status("SUCCESS")
                .results(employeeDto)
                .build();

        log.info("CustomerController::getEmployeeById end");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse>  updateEmployeeById(@PathVariable(name = "id") Long id, @RequestBody CustomerDto employeeBean) throws GenericException {

        log.info("CustomerController::updateEmployeeById start...");

        CustomerDto employeeDto = customerService.updateCustomerById(id, employeeBean);

        APIResponse<CustomerDto> responseDTO = APIResponse
                .<CustomerDto>builder()
                .status("SUCCESS")
                .results(employeeDto)
                .build();

        log.info("CustomerController::updateEmployeeById end");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteEmployeeById(@PathVariable(name = "id") Long id) throws GenericException {
        log.info("CustomerController::deleteEmployeeById start...");

        Boolean res = customerService.deleteCustomerById(id);

        APIResponse<Boolean> responseDTO = APIResponse
                .<Boolean>builder()
                .status("SUCCESS")
                .results(res)
                .build();

        log.info("CustomerController::deleteEmployeeById end");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/update-password")
    public ResponseEntity<APIResponse> updatePassword(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto) throws GenericException {
        log.info("CustomerController::updatePassword start...");

        PasswordChangeResponseDto res = userService.changeUserPassword(passwordChangeRequestDto);

        APIResponse<PasswordChangeResponseDto> responseDTO = APIResponse
                .<PasswordChangeResponseDto>builder()
                .status("SUCCESS")
                .results(res)
                .build();

        log.info("CustomerController::updatePassword end");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/reset-password")
    public ResponseEntity<APIResponse>  resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) throws GenericException {
        log.info("CustomerController::resetPassword start...");

        PasswordChangeResponseDto res = userService.resetPassword(passwordResetRequestDto);

        APIResponse<PasswordChangeResponseDto> responseDTO = APIResponse
                .<PasswordChangeResponseDto>builder()
                .status("SUCCESS")
                .results(res)
                .build();

        log.info("CustomerController::resetPassword end");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
