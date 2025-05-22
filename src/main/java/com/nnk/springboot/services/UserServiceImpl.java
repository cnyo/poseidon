package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUser() {
        return List.of();
    }

    @Override
    public User getUser(Integer id) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User updateUser(Integer id, User user) {
        return null;
    }

    @Override
    public Boolean deleteUser(Integer id) {
        return null;
    }
}
