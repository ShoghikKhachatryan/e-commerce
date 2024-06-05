package com.example.e_commerce.repository;

import com.example.e_commerce.entity.Product;
import com.example.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public User findUserByUsername(String username);
}
