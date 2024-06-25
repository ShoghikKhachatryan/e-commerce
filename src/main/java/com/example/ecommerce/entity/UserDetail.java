package com.example.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_detail")
@Data
@AllArgsConstructor
public class UserDetail {

    public UserDetail(UUID id) {
        this.id = id;
    }

    public UserDetail(UUID id, String fullName) {
        this.id = id;
    }

    @Id
    @Column(name="user_id")
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @OneToOne(mappedBy = "userDetail")
    @JoinColumn(name = "user_id")
    private User user;
}
