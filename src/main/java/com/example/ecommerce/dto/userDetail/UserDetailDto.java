package com.example.ecommerce.dto.userDetail;

import com.example.ecommerce.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {

    @NotNull
    private UUID id;

    @NotNull
    private String fullName;
}
