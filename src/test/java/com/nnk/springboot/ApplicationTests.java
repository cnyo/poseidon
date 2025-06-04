package com.nnk.springboot;

import com.nnk.springboot.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ApplicationTests {

    @Autowired
    private LoginController loginController;

    @Test
    public void contextLoads() {
        assertThat(loginController).isNotNull();
    }
}
