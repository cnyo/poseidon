package com.nnk.springboot.security;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.security.exception.ApplicationAuthenticationException;
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
 * It implements the AuthenticationManager interface and uses UserService to retrieve user details.
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
     *
     * @param authentication the authentication details containing username and password
     * @return an Authentication object if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DbUser dbUser = userService.findByUsername(authentication.getName());

        if (dbUser == null || !passwordEncoder.matches(authentication.getCredentials().toString(), dbUser.getPassword())) {
            throw new ApplicationAuthenticationException("Invalid credentials");
        }

        User authUser = new User(
                dbUser.getUsername(),
                dbUser.getPassword(),
                getGrantedAuthority(dbUser.getRole())
        );

        return new UsernamePasswordAuthenticationToken(authUser, authentication.getCredentials(), authUser.getAuthorities());
    }

    /**
     * Converts the user's role into a list of GrantedAuthority.
     *
     * @param role the role of the user
     * @return a list of GrantedAuthority
     */
    private List<GrantedAuthority> getGrantedAuthority(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return authorities;
    }
}
