package com.nnk.springboot.services;

import com.nnk.springboot.domain.DbUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<DbUser> getAllUser();

    DbUser getUser(Integer id);

    DbUser saveUser(DbUser user);

    DbUser updateUser(Integer id, DbUser user);

    Boolean deleteUser(Integer id);

    DbUser findByUsername(String username);
}
