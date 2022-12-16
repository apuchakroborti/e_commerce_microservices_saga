package com.apu.order.handler;

import com.apu.commons.dto.APIResponse;
import com.apu.commons.dto.ErrorDto;
import com.apu.order.exceptions.CustomerNotFoundException;
import com.apu.order.exceptions.GenericException;
import com.apu.order.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ECommerceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception){
        APIResponse<?> serviceResponse = new APIResponse<>();
        List<ErrorDto> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDto errorDto = new ErrorDto(error.getField(), error.getDefaultMessage());
                    errors.add(errorDto);
                });
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(errors);
        return serviceResponse;
    }

    @ExceptionHandler(GenericException.class)
    public APIResponse<?> handlerServiceException(GenericException exception){
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public APIResponse<?> handlerPayRollNotFoundException(OrderNotFoundException exception){
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto("", exception.getMessage())));
        return serviceResponse;
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public APIResponse<?> handlerEmployeeNotFoundException(CustomerNotFoundException exception){
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto("", exception.getMessage())));
        return serviceResponse;
    }


}
