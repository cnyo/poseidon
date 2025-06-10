package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<DbUser, Integer>, JpaSpecificationExecutor<DbUser> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<DbUser>  findByUsername(String username);
}
