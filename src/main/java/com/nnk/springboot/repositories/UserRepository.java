package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DbUser, Integer>, JpaSpecificationExecutor<DbUser> {

    DbUser findByUsername(String username);
}
