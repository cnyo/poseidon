package com.nnk.springboot.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

@Service
public interface LoginService {
    StringBuffer getUsernamePasswordLoginInfo(Principal user);

    StringBuffer getOauth2LoginInfo(Principal user, OidcUser oidcUser);

    Map<String, String> getOauth2AuthenticationUrls();

    boolean isAnonymousAuthentication(Authentication authentication);
}
