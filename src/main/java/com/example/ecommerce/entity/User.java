package com.example.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private UUID id;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserDetail userDetail;

    public User(UUID userId, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
