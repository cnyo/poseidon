package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.services.LoginService;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;

    @MockitoBean
    private LoginService loginService;

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
    public void getLoginPage_shouldReturnView() throws Exception {
        // arrange
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
        oauth2AuthenticationUrls.put("github", "https://accounts.github.com/o/oauth2/auth");

        // Mock the loginService to return the OAuth2 authentication URLs
        when(loginService.getOauth2AuthenticationUrls()).thenReturn(oauth2AuthenticationUrls);

        // Act
        // Simulate a GET request to the login page
        mockMvc.perform(get("/app/login"))
                .andDo(print()) // Print the result for debugging
                .andExpect(status().isOk()) // Expect HTTP 200 OK status
                .andExpect(view().name("login")) // Expect the view name to be "login"
                .andExpect(model().attributeExists("user")) // Expect the model to contain an attribute "user"
                .andExpect(model().attributeExists("urls")) // Expect the model to contain an attribute "urls"
                .andExpect(content().string(containsString("github"))); // Expect the content to contain "github"
    }

    @Test
    @WithMockUser(username = "user")
    public void getLoginPage_whenNoOauth2Urls_shouldReturnView() throws Exception {
        // arrange
        // Mock the loginService to return the OAuth2 authentication URLs
        when(loginService.getOauth2AuthenticationUrls()).thenReturn(new HashMap<>());

        // Act
        // Simulate a GET request to the login page
        mockMvc.perform(get("/app/login"))
                .andDo(print()) // Print the result for debugging
                .andExpect(status().isOk()) // Expect HTTP 200 OK status
                .andExpect(view().name("login")) // Expect the view name to be "login"
                .andExpect(model().attributeExists("user")) // Expect the model to contain an attribute "user"
                .andExpect(model().attributeExists("urls")) // Expect the model to contain an attribute "urls"
                .andExpect(model().attribute("urls", new HashMap<>())); // Expect the model to contain an empty map for "urls"

    }

    @Test
    @WithMockUser(username = "user")
    public void getAllUserArticlesPage_shouldReturnView() throws Exception {
        // Arrange
        when(userService.getAllUser()).thenReturn(List.of(user));

        // Act
        // Simulate a GET request to the login page
        mockMvc.perform(get("/app/secure/article-details"))
                .andDo(print()) // Print the result for debugging
                .andExpect(status().isOk()) // Expect HTTP 200 OK status
                .andExpect(view().name("user/list")) // Expect the view name to be "login"
                .andExpect(model().attributeExists("users")); // Expect the model to contain an attribute "user"
    }

    @Test
    @WithMockUser(username = "user")
    public void getError403Page_shouldReturnView() throws Exception {
        // Act
        mockMvc.perform(get("/app/error"))
                .andDo(print()) // Print the result for debugging
                .andExpect(status().isOk()) // Expect HTTP 200 OK status
                .andExpect(view().name("403")) // Expect the view name to be "login"
                .andExpect(model().attributeExists("errorMsg")) // Expect the model to contain an attribute "user"
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data.")); // Expect the model to contain an attribute "user"
    }
}
