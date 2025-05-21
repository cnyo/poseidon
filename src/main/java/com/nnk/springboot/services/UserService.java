package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUser();

    User getUser(Integer id);

    User saveUser(User user);

    User updateUser(Integer id, User user);

    Boolean deleteUser(Integer id);
}
