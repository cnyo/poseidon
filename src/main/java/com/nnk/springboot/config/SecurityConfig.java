package com.nnk.springboot.config;

import com.nnk.springboot.security.CustomUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the application.
 * It configures HTTP security, authentication, and authorization.
 */
@EnableMethodSecurity
//@EnableMethodSecurity // Uncomment this line if you want to enable method-level security annotations with @PreAuthorize, @Secured, etc.
@Configuration
public class SecurityConfig {

    private final Logger log = LogManager.getLogger(SecurityConfig.class);

    private final CustomUserDetailsService customUserDetailsService;

    SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Configures the security filter chain for the application.
     * This method sets up form login, OAuth2 login, user details service,
     * and authorization rules for different endpoints.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Configuring security filter chain");

        http
                .formLogin(form ->
                        form.loginPage("/app/login")
                                .defaultSuccessUrl("/") // Redirect to the home page after successful login
                                .permitAll()) // Ensure the login page is accessible to all
                .oauth2Login(form ->
                        form.loginPage("/app/login")
                                .defaultSuccessUrl("/")
                                .permitAll()) // Ensure OAuth2 login is accessible to all
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(matcher -> matcher
                        .requestMatchers("/", "/app/error", "/user/list", "/css/**", "/js/**").permitAll() // Allow public access to these resources
                        .requestMatchers("/app/login").anonymous() // Allow anonymous access to the login page
                        .requestMatchers("/admin/home", "/app/admin/**").hasRole("ADMIN") // Restrict access to admin pages
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .csrf(Customizer.withDefaults()); // Enable CSRF protection with default settings
        log.debug("Security FilterChain configured");

        return http.build();
    }
}
