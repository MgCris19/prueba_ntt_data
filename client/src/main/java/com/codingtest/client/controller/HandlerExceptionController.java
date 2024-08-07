package com.codingtest.client.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.codingtest.client.dto.ApiResponse;
import com.codingtest.client.exception.ClientNotFoundException;
import com.codingtest.client.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler({NullPointerException.class, 
            HttpMessageNotWritableException.class,
            ClientNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> clientNotFoundException(Exception ex){

        Map<String, Object> error = new HashMap<>();
        error.put("date", new Date());
        error.put("error", "el cliente no existe");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }

}
