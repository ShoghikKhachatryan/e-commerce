package com.example.ecommerce.controller;

import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-details")
public class UserDetailController {

    private static final String USER_DETAIL_UUID_PATH = "/{userUuid}";

    private final UserDetailService userDetailService;

    UserDetailController(UserDetailService userService) {
        this.userDetailService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetail>> getAllUserDetails() {
        return ResponseEntity.ok(userDetailService.getAllUserDetails());
    }

    @GetMapping(USER_DETAIL_UUID_PATH)
    public ResponseEntity<UserDetail> getUserDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(userDetailService.getUserDetail(id));
    }

    @PutMapping(USER_DETAIL_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void updateUserDetail(@RequestBody UserDetail userDetail) {
        userDetailService.updateUserDetail(userDetail);
    }
}
