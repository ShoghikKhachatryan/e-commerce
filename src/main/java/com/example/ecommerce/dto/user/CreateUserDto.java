package com.example.ecommerce.dto.user;

import com.example.ecommerce.dto.userDetail.UserDetailDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
