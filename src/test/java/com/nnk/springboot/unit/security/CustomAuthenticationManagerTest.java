package com.nnk.springboot.unit.security;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.security.CustomAuthenticationManager;
import com.nnk.springboot.security.exception.ApplicationAuthenticationException;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomAuthenticationManagerTest {

    @Mock
    UserService mockedUserService;

    @Mock
    PasswordEncoder mockedPasswordEncoder;

    private CustomAuthenticationManager customAuthenticationManager;

    @BeforeEach
    public void setUp() {
        customAuthenticationManager = new CustomAuthenticationManager(mockedUserService, mockedPasswordEncoder);
    }

    @Test
    public void whenAuthenticate_returnAuthentication() {
        // Arrange
        DbUser dbUser = new DbUser();
        dbUser.setUsername("user");
        dbUser.setPassword("password");
        dbUser.setRole("ROLE_USER");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("user", "password");

        when(mockedUserService.findByUsername(anyString())).thenReturn(dbUser);
        when(mockedPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // act
        Authentication result = customAuthenticationManager.authenticate(authRequest);

        // assert
        assertNotNull(result);
        assertEquals("user", result.getName());
    }

    @Test
    public void whenAuthenticate_withBadCredentials_returnException() {
        // Arrange
        DbUser dbUser = new DbUser();
        dbUser.setUsername("user");
        dbUser.setPassword("password");
        dbUser.setRole("ROLE_USER");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("user", "password");

        when(mockedUserService.findByUsername(anyString())).thenReturn(dbUser);
        when(mockedPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // act
        assertThrows(ApplicationAuthenticationException.class, () -> customAuthenticationManager.authenticate(authRequest));
    }

    @Test
    public void whenAuthenticate_withNotExistingUser_returnException() {
        // Arrange
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("user", "password");

        when(mockedUserService.findByUsername(anyString())).thenReturn(null);

        // act
        assertThrows(ApplicationAuthenticationException.class, () -> customAuthenticationManager.authenticate(authRequest));
    }
}
