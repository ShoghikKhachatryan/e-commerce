package com.example.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    private UUID id;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    //mappedBy value points to the relationship owner
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private UserDetail userDetail;

    public User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;

        this.userDetail = new UserDetail();
        this.userDetail.setUser(this);
    }

    public User(UUID id, String username, String password, UserDetail userDetail) {
        this.id = id;
        this.username = username;
        this.password = password;

        if (userDetail == null) {
            userDetail = new UserDetail();
        }

        userDetail.setUser(this);
        this.userDetail = userDetail;
    }
}
