package com.codingtest.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    public static final String CODE = "RESOURCE_NOT_FOUND";

    @Serial
    private static final long serialVersionUID = 3679545332606025185L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}