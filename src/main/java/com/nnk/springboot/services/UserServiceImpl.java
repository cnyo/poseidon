package com.nnk.springboot.services;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<DbUser> getAllUser() {
        return List.of();
    }

    @Override
    public DbUser getUser(Integer id) {
        return null;
    }

    @Override
    public DbUser saveUser(DbUser user) {
        return null;
    }

    @Override
    public DbUser updateUser(Integer id, DbUser user) {
        return null;
    }

    @Override
    public Boolean deleteUser(Integer id) {
        return null;
    }

    @Override
    public DbUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
