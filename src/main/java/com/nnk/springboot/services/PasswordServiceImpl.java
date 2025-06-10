package com.nnk.springboot.services;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PasswordServiceImpl provides the implementation for password validation.
 * It checks if the password meets the specified criteria using a regular expression.
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    private final String REG_EXPRESSION = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%^&+=]).{8,}$";

    /**
     * Validates the password against the defined regular expression.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    @Override
    public Boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(REG_EXPRESSION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
