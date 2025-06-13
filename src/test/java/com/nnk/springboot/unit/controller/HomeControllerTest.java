package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.HomeController;
import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.services.LoginServiceImpl;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
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

    @MockitoBean
    private LoginServiceImpl loginService;

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
    @WithMockUser
    public void getHomePage_shouldReturnView() throws Exception {
        // Arrange
        when(loginService.getDisplayName(any())).thenReturn("username");

        // Act
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getAdminHomePage_shouldReturnView() throws Exception {

        // Act
        mockMvc.perform(get("/admin/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin_home"));
    }
}
