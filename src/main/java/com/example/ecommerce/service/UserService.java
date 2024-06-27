package com.example.ecommerce.service;

import com.example.ecommerce.dto.user.CreateUserDto;
import com.example.ecommerce.dto.user.UserDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.exception.EntityByGivenNameExistException;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        String username = createUserDto.getUsername();

        if (isUserExists(username)) {
            throw new EntityByGivenNameExistException(username);
        }

        User user = mapToEntity(createUserDto);
        user = userRepository.save(user);
        return mapToDto(user);
    }

    @Transactional
    public void deleteUserById(UUID id) {
        if (!isUserExists(id)) {
            throw new EntityNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public UserDto getUser(UUID id) {
        return mapToDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean isUserExists(UUID id) {
        return userRepository.existsById(id);
    }

    private User mapToEntity(CreateUserDto createUserDto) {
        UUID userId = UUID.randomUUID();

        UserDetail userDetail = new UserDetail();
        if (createUserDto.getCreateUserDetailDto() != null) {
            String fullName =  createUserDto.getCreateUserDetailDto().getFullName();
            userDetail.setFullName(fullName);
        }

        return new User(userId, createUserDto.getUsername(), createUserDto.getPassword(), userDetail);
    }

    private UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }
}
