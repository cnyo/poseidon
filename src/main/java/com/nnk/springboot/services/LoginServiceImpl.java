package com.nnk.springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service implementation for handling login-related operations.
 * This includes retrieving OAuth2 authentication URLs, checking for anonymous authentication,
 * and getting the display name of the authenticated user.
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static String AUTORIZATION_REQUEST_BASE_URI = "oauth2/authorization";
    private static String DEFAULT_DISPLAY_USERNAME = "remoteUser";

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private final Logger log = LogManager.getLogger(LoginServiceImpl.class);

    /**
     * Retrieves the OAuth2 authentication URLs for all registered clients.
     *
     * @return a map where the key is the client registration ID and the value is the authorization URL.
     */
    @Override
    public Map<String, String> getOauth2AuthenticationUrls() {
        log.debug("Processing OAuth2 authentication urls");
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            log.debug("ClientRegistrationRepository is iterable");
            // If clientRegistrationRepository is an instance of Iterable<ClientRegistration>
            // we can cast it directly to Iterable<ClientRegistration>
            if (clientRegistrationRepository instanceof InMemoryClientRegistrationRepository repository) {
                log.debug("ResolvableType {}", type);
                clientRegistrations = StreamSupport
                        .stream(repository.spliterator(), false)
                        .collect(Collectors.toList());
            } else {
                log.debug("ClientRegistrationRepository is not iterable");
                throw new IllegalStateException("ClientRegistrationRepository is not iterable");
            }
        }

        if (clientRegistrations == null) {
            log.debug("No client registrations found");
            return oauth2AuthenticationUrls;
        }

        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), "/" + AUTORIZATION_REQUEST_BASE_URI + "/" + registration.getRegistrationId()));

        log.debug("Returning {} oauth2 authentication urls", oauth2AuthenticationUrls.size());

        return oauth2AuthenticationUrls;
    }

    /**
     * Checks if the given authentication is an anonymous authentication.
     *
     * @param authentication the authentication object to check.
     * @return true if the authentication is anonymous, false otherwise.
     */
    @Override
    public boolean isAnonymousAuthentication(Authentication authentication) {
        if (authentication == null) {
            log.debug("Authentication object is null");
            return true;
        }

        if (authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            log.debug("User is already authenticated, redirecting to home page");
            return true;
        }

        log.debug("User is not authenticated or is not an anonymous user");
        return false;
    }

    /**
     * Retrieves the display name for the given authentication.
     * This could be the username from UserDetails or OAuth2User, or a default value if not available.
     *
     * @param authentication the authentication object.
     * @return the display name, which could be the username or a default value if not available.
     */
    @Override
    public String getDisplayName(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.debug("Authentication object is null or not authenticated, returning default display name");
            return DEFAULT_DISPLAY_USERNAME;
        }

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User oAuth2User) {
                String name = oAuth2User.getAttribute("name");
                if (name != null && !name.isBlank()) {
                    log.debug("Returning OAuth2 user name");
                    return name;
                }
            }

            if (principal instanceof UserDetails userDetails) {
                String username = userDetails.getUsername();
                if (username != null && !username.isBlank()) {
                    log.debug("Returning UserDetails username");
                    return username;
                }
            }
        }

        return DEFAULT_DISPLAY_USERNAME;
    }
}
