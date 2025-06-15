package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.security.user.AuthUser;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService; // Mock the UserService dependency

//    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        // Initialize the CustomUserDetailsService with the mocked UserService
        customUserDetailsService = new CustomUserDetailsService(userService);
    }

    @Test
    public void loadUserByUsername_shouldReturnDbUser() {
        // Arrange
        String username = "testUser";
        DbUser dbUser = new DbUser();
        dbUser.setUsername(username);
        dbUser.setId(1);
        dbUser.setPassword("testPassword");
        dbUser.setFullname("Test User");
        dbUser.setRole("USER");

        when(userService.getUserByUsername(anyString())).thenReturn(dbUser);

        // Act
        UserDetails result = customUserDetailsService.loadUserByUsername(username);

        // Assert
        assertThat(result).isInstanceOf(AuthUser.class);
        assertThat(result.getUsername()).isEqualTo(username);
    }

    @Test
    public void loadUserByUsername_whenDbUserNotExists_shouldThrowExceptions() {
        // Arrange
        String username = "testUser";

        when(userService.getUserByUsername(anyString())).thenReturn(null);

        // Act and Assert
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(username))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found with username");
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    @NullSource
    public void loadUserByUsername_whenUsernameIsNullOrEmpty_shouldThrowExceptions(String username) {
        // Act and Assert
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(username))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Username is null or empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    @NullSource
    public void loadUserByUsername_whenRoleIsNullOrEmpty_shouldThrowExceptions(String role) {
        // Arrange
        String username = "testUser";
        DbUser dbUser = new DbUser();
        dbUser.setUsername(username);
        dbUser.setId(1);
        dbUser.setPassword("testPassword");
        dbUser.setFullname("Test User");
        dbUser.setRole(role);

        when(userService.getUserByUsername(anyString())).thenReturn(dbUser);

        // Act
        UserDetails result = customUserDetailsService.loadUserByUsername(username);

        // Assert
        assertThat(result).isInstanceOf(AuthUser.class);
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getAuthorities()).isEmpty();
    }
}
