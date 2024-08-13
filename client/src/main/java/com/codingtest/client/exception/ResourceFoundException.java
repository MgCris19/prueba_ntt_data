package com.codingtest.client.exception;

import java.io.Serial;

public class ResourceFoundException extends Exception {

    public static final String CODE = "RESOURCE_FOUND";

    @Serial
    private static final long serialVersionUID = 3679545332606025185L;

    public ResourceFoundException(String message) {
        super(message);
    }
}