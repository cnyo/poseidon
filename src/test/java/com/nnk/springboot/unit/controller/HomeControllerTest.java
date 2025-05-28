package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.HomeController;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;

    private DbUser user;

    @BeforeEach
    public void setUp() {
        user = new DbUser();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("password");
        user.setFullname("userFullname");
        user.setRole("USER");
    }

    @Test
    @WithMockUser(username = "user")
    public void getHomePage_shouldReturnView() throws Exception {

        // Act
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getAdminHomePage_shouldReturnView() throws Exception {

        // Act
        mockMvc.perform(get("/admin/home"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
