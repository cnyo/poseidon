package com.nnk.springboot.config;

import com.nnk.springboot.security.CustomAuthenticationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the application.
 * It configures HTTP security, authentication, and authorization.
 */
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final Logger log = LogManager.getLogger(SecurityConfig.class);

    private final CustomAuthenticationManager customAuthenticationManager;

    SecurityConfig(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object to configure
     * @return a SecurityFilterChain instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Configuring security filter chain");

        http
                .formLogin(form ->
                        form.loginPage("/app/login")
                        .permitAll())
                .authenticationManager(customAuthenticationManager)
                .authorizeHttpRequests(matcher -> matcher
                        .requestMatchers("/", "/app/login", "/app/error", "/user/list", "/css/**", "/js/**")
                        .permitAll())
                .authorizeHttpRequests(matcher -> matcher
                        .requestMatchers( "/admin/home", "/app/admin/**").hasRole("ADMIN"))
                .authorizeHttpRequests(matcher -> matcher.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(customize -> customize
                        .accessDeniedPage("/accessDenied"))
        ;
        log.debug("Security FilterChain configured");

        return http.build();
    }
}
