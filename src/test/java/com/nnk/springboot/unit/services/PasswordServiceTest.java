package com.nnk.springboot.unit.services;

import com.nnk.springboot.services.PasswordService;
import com.nnk.springboot.services.PasswordServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class PasswordServiceTest {

    private final PasswordService passwordService = new PasswordServiceImpl();

    @ParameterizedTest
    @ValueSource(strings = {"Valid123@", "Valid123abcD@", "A1@b2#c3$d4%e5^f6&g7*h8(i9)j0"})
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
            "Abcfg1@" // Valid but too short
    })
    @NullSource
    public void testValidity_whenPasswordIsNotValid_shouldReturnTrue(String password) {
        // Act
        Boolean result = passwordService.isValidPassword(password);

        // Assert
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    @NullSource
    public void testValidity_emptyPassword_shouldThrowException(String password) {
        // Act & Assert
        assertThatThrownBy(() -> passwordService.encodePassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password cannot be null or empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Valid123@", "Valid123abcD@", "A1@b2#c3$d4%e5^f6&g7*h8(i9)j0"})
    public void testValidity_goodPassword_shouldReturnEncodePassword(String password) {
        // act
        String result = passwordService.encodePassword(password);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).isNotEqualTo(password); // Ensure the password is encoded
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567", // Too short
            "abcdefgh", // No uppercase, no digit, no special character
            "ABCDEFGH", // No lowercase, no digit, no special character
            "Abcdefgh", // No digit, no special character
            "Abcdefg1", // No special character
            "Abcdefg@", // No digit
            "Abcfg1@" // Valid but too short
    })
    public void testValidity_badPassword_shouldThrowException(String password) {
        // Act & Assert
        assertThatThrownBy(() -> passwordService.encodePassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid password format: ");
    }
}
