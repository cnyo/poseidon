package com.nnk.springboot.security;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.security.user.AuthUser;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final Logger log = LogManager.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername");
        DbUser dbUser = userService.findByUsername(username);

        return new AuthUser(
                dbUser.getId(),
                dbUser.getUsername(),
                dbUser.getPassword(),
                dbUser.getFullname(),
                List.of(dbUser.getRole())
        );
    }
}
