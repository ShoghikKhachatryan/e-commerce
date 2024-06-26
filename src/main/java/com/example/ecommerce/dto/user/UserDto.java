package com.example.ecommerce.dto.user;

import com.example.ecommerce.dto.userDetail.UserDetailDto;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Id
    private UUID id;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private UserDetailDto userDetailDto;

    public UserDto(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        userDetailDto = new UserDetailDto(id);
    }
}
