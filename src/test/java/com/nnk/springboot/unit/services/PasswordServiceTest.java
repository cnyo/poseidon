package com.nnk.springboot.unit.services;

import com.nnk.springboot.services.PasswordService;
import com.nnk.springboot.services.PasswordServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PasswordServiceTest {

    private final PasswordService passwordService = new PasswordServiceImpl();

    @ParameterizedTest
    @ValueSource(strings = {"Valid123@", "Valid123abcD@"})
    public void testValidity_whenPasswordIsValid_shouldReturnTrue(String password) {
        // Act
        Boolean result = passwordService.isValidPassword(password);

        // Assert
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", // Empty password
            "1234567", // Too short
            "abcdefgh", // No uppercase, no digit, no special character
            "ABCDEFGH", // No lowercase, no digit, no special character
            "Abcdefgh", // No digit, no special character
            "Abcdefg1", // No special character
            "Abcdefg@", // No digit
            "Abcfg1@", // Valid but too short
            "A1@b2#c3$d4%e5^f6&g7*h8(i9)j0" // Too long
    })
    @NullSource
    public void testValidity_whenPasswordIsNotValid_shouldReturnTrue(String password) {
        // Act
        Boolean result = passwordService.isValidPassword(password);

        // Assert
        assertThat(result).isFalse();
    }
}
