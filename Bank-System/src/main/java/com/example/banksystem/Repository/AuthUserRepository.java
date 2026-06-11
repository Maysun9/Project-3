package com.example.banksystem.Repository;

import com.example.banksystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findUserByEmail(String email);
}