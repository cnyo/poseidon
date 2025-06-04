package com.nnk.springboot.controllers;

import com.nnk.springboot.services.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OAuth2LoginController {

    private final Logger log = LogManager.getLogger(OAuth2LoginController.class);

    @Autowired
    private LoginService loginService;

    @GetMapping("/github")
    public String getUserInfo(Principal user, @AuthenticationPrincipal OidcUser oidcUser) {
        log.info("Accessing GitHub login endpoint");
        StringBuffer userInfo = new StringBuffer();

        if (user instanceof UsernamePasswordAuthenticationToken) {
            log.info("User is authenticated with UsernamePasswordAuthenticationToken");
            userInfo.append(loginService.getUsernamePasswordLoginInfo(user));
        } else if (user instanceof OAuth2AuthenticationToken) {
            log.info("User is authenticated with OAuth2AuthenticationToken");
            userInfo.append(loginService.getOauth2LoginInfo(user, oidcUser));
        }

        return userInfo.toString();
    }
}
