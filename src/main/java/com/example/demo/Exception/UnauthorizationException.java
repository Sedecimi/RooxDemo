package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Runtime excpetion for unauthorized access
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizationException extends RuntimeException {
    public UnauthorizationException() {
        super();
    }

    public UnauthorizationException(String message) {
        super(message);
    }

    public UnauthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
