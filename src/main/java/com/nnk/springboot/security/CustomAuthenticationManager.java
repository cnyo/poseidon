package com.nnk.springboot.security;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomAuthenticationManager is responsible for authenticating users based on their credentials.
 * It checks the provided username and password against the stored user data.
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    CustomAuthenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates the user based on the provided authentication details.
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DbUser userEntity = userService.findByUsername(authentication.getName());

        if (userEntity == null || !passwordEncoder.matches(authentication.getCredentials().toString(), userEntity.getPassword())) {
            throw new AuthenticationException("Invalid credentials") {};
        }

        User authUser = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
               getGrantedAuthority("ADMIN") // Assuming a default role for simplicity
        );

        return new UsernamePasswordAuthenticationToken(authUser, authentication.getCredentials(), authUser.getAuthorities());
    }

    /**
     * Converts a role string into a list of GrantedAuthority.
     * @param role
     * @return
     */
    private List<GrantedAuthority> getGrantedAuthority(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return authorities;
    }
}
