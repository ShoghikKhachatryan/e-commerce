package com.example.ecommerce.dto.userDetail;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDetailDto {
    @NotNull
    private UUID id;

    @NotNull
    private String fullName;
}
