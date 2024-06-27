package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_detail")
@Data
@NoArgsConstructor
public class UserDetail {

    @Id
    @Column(name="user_id")
    private UUID id;

    private String fullName;

    // @MapsId maps to the parent entity primary key
    // optional = false the userDetail entity cannot be persisted when it's associated user is null.
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserDetail(UUID id) {
        this.id = id;
    }

    public UserDetail(UUID id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

}
