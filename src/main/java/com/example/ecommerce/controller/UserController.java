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

    private static final String USER_UUID_PATH = "/{userUuid}";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        final UserDto userDto = userService.createUser(createUserDto);

        // Assuming the location URI is determined by the product ID or some other logic
        URI location = URI.create(USER_PATH + "/" + userDto.getId());
        return ResponseEntity.created(location).body(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(USER_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @DeleteMapping(USER_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable UUID userUuid) {
        userService.deleteUserById(userUuid);
    }
}
