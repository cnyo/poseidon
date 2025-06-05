package com.nnk.springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LoginServiceImpl implements LoginService {
    private static String AUTORIZATION_REQUEST_BASE_URI = "oauth2/authorization";

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private final Logger log = LogManager.getLogger(LoginServiceImpl.class);

    @Override
    public StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        log.debug("Processing UsernamePassword login info");
        StringBuffer usernameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;

        if (token.isAuthenticated()) {
            log.info("Authenticated user");
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome, " + u.getUsername());
        } else {
            log.info("Not authenticated user");
            usernameInfo.append("NA");
        }

        log.info("Returning username info: {}", usernameInfo);
        return usernameInfo;
    }

    @Override
    public StringBuffer getOauth2LoginInfo(Principal user, OidcUser oidcUser) {
        log.debug("Processing OAuth2 login info");
        StringBuffer protectedInfo = new StringBuffer();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) user;

        OAuth2AuthorizedClient authClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(), token.getName()
        );

        if (token.isAuthenticated()) {
            log.info("Authenticated");
            Map<String, Object> attributes = ((DefaultOAuth2User) token.getPrincipal()).getAttributes();
            String accessToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, " + attributes.get("name") + "<br><br>");
            protectedInfo.append("e-mail : " + attributes.get("email") + "<br><br>");
            protectedInfo.append("Access token: " + accessToken);

            if (oidcUser != null) {
                log.debug("OIDC has oidcUser");
                OidcIdToken idToken = oidcUser.getIdToken();
                if (idToken != null) {
                    log.debug("OIDC get IDToken");
                    Map<String, Object> claims = idToken.getClaims();
                    for (String key : claims.keySet()) {
                        protectedInfo.append(" " + key + ": " + claims.get(key) + "<br>");
                    }
                }
            }

        } else {
            log.info("Not authenticated");
            protectedInfo.append("NA");
        }

        log.info("Returning protected info: {}", protectedInfo);
        return protectedInfo;
    }

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

    @Override
    public boolean isAnonymousAuthentication(Authentication authentication) {
        if (authentication == null) {
            log.debug("Authentication object is null");
            return true;
        }

        if (authentication.isAuthenticated() && authentication instanceof AnonymousAuthenticationToken) {
            log.debug("User is already authenticated, redirecting to home page");
            return true;
        }

        log.debug("User is not authenticated or is not an anonymous user");
        return false;
    }

    @Override
    public String getDisplayName(Authentication authentication) {
        String displayName = "remoteUser";

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User oAuth2User) {
                displayName = oAuth2User.getAttribute("name");
            }

            if (principal instanceof UserDetails userDetails) {
                displayName = userDetails.getUsername();
            }
        }

        return displayName;
    }
}
