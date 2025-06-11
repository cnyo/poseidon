package com.nnk.springboot.services;

import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * Service interface for handling login-related operations.
 * This includes retrieving OAuth2 authentication URLs, checking for anonymous authentication,
 * and getting the display name of the authenticated user.
 */
public interface LoginService {

    /**
     * Retrieves the OAuth2 authentication URLs for all registered clients.
     *
     * @return a map where the key is the client registration ID and the value is the authorization URL.
     */
    Map<String, String> getOauth2AuthenticationUrls();

    /**
     * Checks if the given authentication is an anonymous authentication.
     *
     * @param authentication the authentication object to check.
     * @return true if the authentication is anonymous, false otherwise.
     */
    boolean isAnonymousAuthentication(Authentication authentication);

    /**
     * Retrieves the display name for the given authentication.
     *
     * @param authentication the authentication object.
     * @return the display name, which could be the username or a default value if not available.
     */
    String getDisplayName(Authentication authentication);

    /**
     * Checks if the user associated with the given authentication has an admin role.
     *
     * @param authentication the authentication object to check.
     * @return true if the user has an admin role, false otherwise.
     */
    boolean hasAdminRole(Authentication authentication);
}
