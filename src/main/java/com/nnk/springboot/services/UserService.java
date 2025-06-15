package com.nnk.springboot.services;

import com.nnk.springboot.domain.DbUser;

import java.util.List;

/**
 * Service interface for managing users.
 * Provides methods to perform CRUD operations on user data.
 */
public interface UserService {
    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    List<DbUser> getAllUser();

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws IllegalArgumentException if the ID is null or invalid
     */
    DbUser getUser(Integer id) throws IllegalArgumentException;

    /**
     * Saves a new user.
     *
     * @param user the user to save
     * @return the saved user
     * @throws IllegalArgumentException if the user is null or invalid
     */
    DbUser saveUser(DbUser user) throws IllegalArgumentException;

    /**
     * Updates an existing user.
     *
     * @param id   the ID of the user to update
     * @param user the updated user data
     * @return the updated user
     * @throws IllegalArgumentException if the ID is null or invalid, or if the user data is invalid
     */
    DbUser updateUser(Integer id, DbUser user) throws IllegalArgumentException;

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws IllegalArgumentException if the ID is null or invalid
     */
    void deleteUser(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the specified username
     * @throws IllegalArgumentException if the username is null or empty
     */
    DbUser getUserByUsername(String username) throws IllegalArgumentException;
}
