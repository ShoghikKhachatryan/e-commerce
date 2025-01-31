package com.example.ecommerce.controller;

import com.example.ecommerce.dto.userDetail.UpdateUserDetailDto;
import com.example.ecommerce.dto.userDetail.UserDetailDto;
import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-details")
public class UserDetailController {

    private static final String USER_DETAIL_PATH = "/user-details";

    private static final String USER_DETAIL_UUID_PATH = "/{userUuid}";

    private final UserDetailService userDetailService;

    UserDetailController(UserDetailService userService) {
        this.userDetailService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDetailDto> getAllUserDetails() {
        return userDetailService.getAllUserDetails();
    }

    @GetMapping(USER_DETAIL_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public UserDetailDto getUserDetail(@PathVariable UUID id) {
        return userDetailService.getUserDetail(id);
    }

    @PutMapping(USER_DETAIL_UUID_PATH)
    public ResponseEntity<UserDetailDto> updateUserDetail(@PathVariable UUID id, @RequestBody UpdateUserDetailDto updateUserDetailDto) {
        updateUserDetailDto.setId(id);
        UserDetailDto userDetailDto = userDetailService.updateUserDetail(updateUserDetailDto);
        URI location = URI.create(USER_DETAIL_PATH + "/" + userDetailDto.getId());
        return ResponseEntity.status(HttpStatus.OK).location(location).body(userDetailDto);
    }
}
