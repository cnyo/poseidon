package com.nnk.springboot.services;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing users.
 * Provides methods to perform CRUD operations on user data.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Override
    public List<DbUser> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID, or null if not found
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public DbUser getUser(Integer id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return userRepository.findById(id).orElse(null);
    }

    /**
     * Saves a new user.
     *
     * @param user the user to save
     * @return the saved user
     * @throws IllegalArgumentException if the user is null or invalid
     */
    @Override
    public DbUser saveUser(DbUser user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getUsername() == null || user.getPassword() == null || user.getFullname() == null) {
            throw new IllegalArgumentException("User fields cannot be null");
        }

        if (user.getRole() == null) {
            user.setRole("USER"); // Default role if not set
        }

        return userRepository.save(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id   the ID of the user to update
     * @param user the updated user data
     * @return the updated user, or null if not found
     * @throws IllegalArgumentException if the ID is null or invalid, or if the user data is invalid
     */
    @Override
    public DbUser updateUser(Integer id, DbUser user) throws IllegalArgumentException {
        if (id == null || user == null) {
            throw new IllegalArgumentException("User ID and User cannot be null");
        }

        DbUser existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setFullname(user.getFullname());
            existingUser.setRole(user.getRole() != null ? user.getRole() : "USER"); // Default role if not set

            return userRepository.save(existingUser);
        }

        return null;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public void deleteUser(Integer id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        userRepository.deleteById(id);
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the user with the specified username, or null if not found
     * @throws IllegalArgumentException if the username is null or empty
     */
    @Override
    public DbUser findByUsername(String username) throws IllegalArgumentException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        return userRepository.findByUsername(username);
    }
}
