package com.example.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetail {

    @Id
    @Column(name="user_id")
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @MapsId
    @OneToOne(mappedBy = "userDetail")
    @JoinColumn(name = "user_id")
    private User user;
}
