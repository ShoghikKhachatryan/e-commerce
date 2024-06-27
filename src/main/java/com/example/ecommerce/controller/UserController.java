package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.CreateUserDto;
import com.example.ecommerce.dto.user.UserDto;
import com.example.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String USER_PATH = "/users";

    private static final String USER_UUID_PATH = "/{userId}";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        final UserDto userDto = userService.createUser(createUserDto);

        URI location = URI.create(USER_PATH + "/"+ userDto.getId());
        return ResponseEntity.created(location).body(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(USER_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping(USER_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
    }
}
