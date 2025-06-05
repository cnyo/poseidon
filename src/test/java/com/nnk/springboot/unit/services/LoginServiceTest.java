package com.nnk.springboot.unit.services;

import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private AnonymousAuthenticationToken anonymousAuthenticationToken;

    @Mock
    private Authentication authentication;

    @Mock
    private OAuth2User oAuth2User;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private LoginServiceImpl loginService;


    @Test
    public void getIsAnonymousAuthentication_whenAuthenticatedAndIsAnonymous_returnTrue() throws IllegalArgumentException {
        // Arrange
        when(anonymousAuthenticationToken.isAuthenticated()).thenReturn(true);

        // Act
        Boolean result = loginService.isAnonymousAuthentication(anonymousAuthenticationToken);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void getIsAnonymousAuthentication_whenIsNotAuthenticated_returnTrue() throws IllegalArgumentException {
        // Arrange
        when(anonymousAuthenticationToken.isAuthenticated()).thenReturn(false);

        // Act
        Boolean result = loginService.isAnonymousAuthentication(anonymousAuthenticationToken);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void getIsAnonymousAuthentication_whenAuthenticationIsNull_returnTrue() throws IllegalArgumentException {
        // Act
        Boolean result = loginService.isAnonymousAuthentication(null);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void getIsAnonymousAuthentication_whenIsNotAnonymous_returnTrue() throws IllegalArgumentException {
        // Act
        Boolean result = loginService.isAnonymousAuthentication(authentication);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void getDisplayName_whenOAuth2_shouldReturnString() throws IllegalArgumentException {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute(anyString())).thenReturn("oAuth2User_username");

        // Act
        String result = loginService.getDisplayName(authentication);

        // Assert
        assertThat(result).isEqualTo("oAuth2User_username");
    }

    @Test
    public void getDisplayName_whenUserDetails_shouldReturnString() throws IllegalArgumentException {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("userDetails_username");

        // Act
        String result = loginService.getDisplayName(authentication);

        // Assert
        assertThat(result).isEqualTo("userDetails_username");
    }

    @Test
    public void getDisplayName_whenOAuth2UserNameIsBlank_shouldReturnDefaultString() throws IllegalArgumentException {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute(anyString())).thenReturn("");

        // Act
        String result = loginService.getDisplayName(authentication);

        // Assert
        assertThat(result).isEqualTo("remoteUser");
    }

    @Test
    public void getDisplayName_whenUserDetailsUsernameIsBlank_shouldReturnDefaultString() throws IllegalArgumentException {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("");

        // Act
        String result = loginService.getDisplayName(authentication);

        // Assert
        assertThat(result).isEqualTo("remoteUser");
    }

    @Test
    public void getDisplayName_whenAuthenticationIsNull_shouldReturnDefaultString() throws IllegalArgumentException {
        // Act
        String result = loginService.getDisplayName(null);

        // Assert
        assertThat(result).isEqualTo("remoteUser");
    }

    @Test
    public void getDisplayName_whenIsNotAuthenticated_shouldReturnDefaultString() throws IllegalArgumentException {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act
        String result = loginService.getDisplayName(authentication);

        // Assert
        assertThat(result).isEqualTo("remoteUser");
    }
}
