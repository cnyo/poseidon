package com.nnk.springboot.security;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.security.exception.ApplicationAuthenticationException;
import com.nnk.springboot.security.user.AuthUser;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final Logger log = LogManager.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ApplicationAuthenticationException {
        log.debug("loadUserByUsername");
        if (username == null || username.isEmpty()) {
            log.error("Username is null or empty");
            throw new ApplicationAuthenticationException("Username is null or empty");
        }

        try {
            log.info("Loading user by username: {}", username);
            DbUser dbUser = userService.getUserByUsername(username);

            if (dbUser == null) {
                log.error("User not found with username: {}", username);
                throw new ApplicationAuthenticationException("User not found with username");
            }

            return new AuthUser(
                    dbUser.getId(),
                    dbUser.getUsername(),
                    dbUser.getPassword(),
                    dbUser.getFullname(),
                    getGrantedAuthority(dbUser.getRole())
            );
        } catch (Exception e) {
            log.error("Error loading user by username: {}", username, e);
            throw new ApplicationAuthenticationException("User not found with username");
        }

    }

    /**
     * Converts the user's role into a list of GrantedAuthority.
     *
     * @param role the role of the user
     * @return a list of GrantedAuthority
     */
    private List<String> getGrantedAuthority(String role) {
        if (role == null || role.isEmpty()) {
            log.warn("Role is null or empty, returning empty authorities list");
            return new ArrayList<>();
        }

        log.debug("getGrantedAuthority for role: {}", role);
        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_" + role);

        return authorities;
    }
}
