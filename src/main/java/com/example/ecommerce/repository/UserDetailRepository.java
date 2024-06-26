package com.example.ecommerce.repository;

import com.example.ecommerce.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, UUID> {
    @Modifying
    @Query("update UserDetail u set u.fullName = :fullName where u.id = :id")
    void updateFullName(UUID id, String fullName);
}
