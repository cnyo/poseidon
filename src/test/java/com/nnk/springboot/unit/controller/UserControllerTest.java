package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.services.PasswordService;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;

    @MockitoBean
    private PasswordService passwordService;

    static DbUser user;

    @BeforeEach
    public void setUp() {
        user = new DbUser();
        user.setId(1);
    }

    @Test
    @WithMockUser(username = "user")
    public void getAllUser_thenReturnUserListView() throws Exception {
        // Arrange
        List<DbUser> users = List.of(user);

        when(userService.getAllUser()).thenReturn(users);

        // Act
        ResultActions result = mockMvc.perform(get("/user/list"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", users))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user")
    public void getAddUserForm_thenReturnUserFormView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/user/add"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "user")
    public void postUser_withValidatePassword_thenRedirectToUserListView() throws Exception {
        // Arrange
        when(passwordService.isValidPassword(anyString())).thenReturn(true);
        when(userService.saveUser(any())).thenReturn(user);

        // Act
        ResultActions result = mockMvc.perform(post("/user/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "role"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void postUser_withUnValidatePassword_thenRedirectToUserListView() throws Exception {
        // Arrange
        when(passwordService.isValidPassword(anyString())).thenReturn(false);

        // Act
        ResultActions result = mockMvc.perform(post("/user/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "role"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser
    public void getUserById_thenShowUpdateFormView() throws Exception {
        // Arrange
        when(userService.getUser(any())).thenReturn(user);

        // Act
        ResultActions result = mockMvc.perform(get("/user/update/{id}", user.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateUser_withValidatePassword_thenRedirectToUserListView() throws Exception {
        // Arrange
        when(userService.updateUser(anyInt(), any())).thenReturn(user);
        when(passwordService.isValidPassword(anyString())).thenReturn(true);

        // Act
        ResultActions result = mockMvc.perform(post("/user/update/{id}", user.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "role"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateUser_withInvalidatePassword_thenUpdateView() throws Exception {
        // Arrange
        when(userService.updateUser(anyInt(), any())).thenReturn(user);
        when(passwordService.isValidPassword(anyString())).thenReturn(false);

        // Act
        ResultActions result = mockMvc.perform(post("/user/update/{id}", user.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "role"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteUser_thenRedirectUserListView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/user/delete/{id}", user.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }
}
