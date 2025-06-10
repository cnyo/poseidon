package com.nnk.springboot.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Custom exception class for handling application-specific authentication errors.
 * This class extends AuthenticationException to provide a specific type of exception
 * that can be thrown during the authentication process.
 */
public class ApplicationAuthenticationException extends AuthenticationException {
    public ApplicationAuthenticationException(String msg) {
        super(msg);
    }
}