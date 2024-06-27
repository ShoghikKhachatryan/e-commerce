package com.example.ecommerce.controller;

import com.example.ecommerce.dto.userDetail.UpdateUserDetailDto;
import com.example.ecommerce.dto.userDetail.UserDetailDto;
import com.example.ecommerce.service.UserDetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/user-details")
public class UserDetailController {

    private static final String USER_DETAIL_UUID_PATH = "/{userId}";

    private final UserDetailService userDetailService;

    UserDetailController(UserDetailService userService) {
        this.userDetailService = userService;
    }

    @GetMapping(USER_DETAIL_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public UserDetailDto getUserDetail(@PathVariable UUID userId) {
        return userDetailService.getUserDetail(userId);
    }

    @PutMapping(USER_DETAIL_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public UserDetailDto updateUserDetail(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUserDetailDto updateUserDetailDto
    ) {
        UserDetailDto userDetailDto = userDetailService.updateUserDetail(userId, updateUserDetailDto);

        return userDetailDto;
    }
}
