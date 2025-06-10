package com.nnk.springboot.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents an authenticated user in the application.
 * Implements both OAuth2User and UserDetails interfaces to provide user details
 * and authorities for authentication and authorization purposes.
 *
 * @param id       the unique identifier of the user
 * @param username the username of the user
 * @param password the password of the user
 * @param fullname the full name of the user
 * @param roles    the roles assigned to the user
 */

public record AuthUser(
        Integer id,
        String username,
        String password,
        String fullname,
        List<String> roles
) implements OAuth2User, UserDetails {

    /**
     * Returns the unique identifier of the user.
     * This method is used to retrieve the user's ID for various operations.
     *
     * @return the unique identifier of the user
     */
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    /**
     * Returns the password of the user.
     * This method is used for authentication purposes.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the attributes of the user.
     * In this implementation, it returns an empty map as no additional attributes are provided.
     *
     * @return an empty map representing the user's attributes
     */
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    /**
     * Returns the authorities granted to the user.
     * This method converts the list of roles into a collection of GrantedAuthority objects.
     *
     * @return a collection of GrantedAuthority objects representing the user's roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new).toList();
    }

    /**
     * Returns the full name of the user.
     * This method is used to provide a human-readable name for the user.
     *
     * @return the full name of the user
     */
    @Override
    public String getName() {
        return fullname;
    }

    /**
     * Indicates whether the user's account is non-expired.
     * In this implementation, it always returns true.
     *
     * @return true, indicating the account is non-expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is non-locked.
     * In this implementation, it always returns true.
     *
     * @return true, indicating the account is non-locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are non-expired.
     * In this implementation, it always returns true.
     *
     * @return true, indicating the credentials are non-expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     * In this implementation, it always returns true.
     *
     * @return true, indicating the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
