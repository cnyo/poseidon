package com.nnk.springboot.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/app/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void userLoginTest_redirectedToHomePage() throws Exception {
        // Perform a login request with valid credentials
        mockMvc.perform(formLogin("/app/login")
                .user("admin")
                .password("admin")
            )
            .andDo(print())
            .andExpect(authenticated())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

    }

    @Test
    public void userLoginFailed() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                .user("admin")
                .password("user")
            )
            .andDo(print())
            .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    public void shouldReturnUserPage() throws Exception {
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
        mockMvc.perform(get("/bidList/add")).andDo(print()).andExpect(status().isOk());
    }
}
