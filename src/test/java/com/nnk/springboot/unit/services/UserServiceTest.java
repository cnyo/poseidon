package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
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
    public void getAllUser_mustReturnUserList() throws IllegalArgumentException {
        // Arrange
        List<DbUser> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<DbUser> result = userService.getAllUser();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().findFirst().isPresent());
        Assertions.assertNotNull(result.stream().findFirst().get().getId());
        Assertions.assertEquals("username", result.stream().findFirst().get().getUsername());
    }

    @Test
    public void saveUser_mustReturnUser() throws IllegalArgumentException {
        // Arrange
        when(userRepository.save(any())).thenReturn(user);

        // Act
        DbUser result = userService.saveUser(user);

        // Assert
        Assert.assertNotNull(result.getId());
        Assert.assertEquals("username", result.getUsername());
    }

    @Test
    public void saveUser_withNullData_mustReturnException() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(null));

        String expectedMessage = "User cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getUser_mustReturnUser() throws IllegalArgumentException {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user));

        // Act
        DbUser result = userService.getUser(10);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(DbUser.class, result);
        Assertions.assertEquals("username", result.getUsername());
    }

    @Test
    public void updateUser_mustReturnUser() throws IllegalArgumentException {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user));
        user.setUsername("username updated");
        when(userRepository.save(any())).thenReturn(user);

        // Act
        DbUser result = userService.updateUser(10, user);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(DbUser.class, result);
        Assert.assertEquals("username updated", result.getUsername());
    }

    @Test
    public void deleteUser_shouldCallDeleteById() throws IllegalArgumentException {
        // Act
        userService.deleteUser(user.getId());

        // Assert
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void getUserByUsername_shouldReturnUser() throws IllegalArgumentException {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        // Act
        DbUser result = userService.getUserByUsername("username");

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(DbUser.class, result);
        Assert.assertEquals("username", result.getUsername());
    }
}
