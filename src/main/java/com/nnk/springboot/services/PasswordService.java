package com.nnk.springboot.services;

/**
 * PasswordService provides methods to validate passwords.
 * It checks if the password meets specific criteria such as length, character types, etc.
 */
public interface PasswordService {

    /**
     * Validates the password against defined criteria.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    Boolean isValidPassword(String password);
}