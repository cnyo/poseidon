package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** * Configuration class for password encoding.
 * This class provides a bean for encoding passwords using BCrypt.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates a PasswordEncoder bean that uses BCrypt for password encoding.
     *
     * @return a PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
