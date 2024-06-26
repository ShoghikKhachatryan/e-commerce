package com.example.ecommerce.service;

import com.example.ecommerce.dto.user.CreateUserDto;
import com.example.ecommerce.dto.user.UserDto;
import com.example.ecommerce.dto.userDetail.UserDetailDto;
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

        if (isExistUser(username)) {
            throw new EntityByGivenNameExistException(username);
        }

        User user = mapToEntity(createUserDto);
        userRepository.save(user);
        return mapToDto(user);
    }

    @Transactional
    public void deleteUserById(UUID id) {
        if (!isExistUser(id)) {
            throw new EntityNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public UserDto getUser(UUID id) {
        return mapToDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    public UserDto getUser(String username) {
        return mapToDto(userRepository.findUserByUsername(username));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private boolean isExistUser(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean isExistUser(UUID id) {
        return userRepository.existsById(id);
    }

    private User mapToEntity(CreateUserDto createUserDto) {
        UUID userId = UUID.randomUUID();

        UserDetail userDetail = new UserDetail();
        if (createUserDto.getCreateUserDetailDto() != null) {
            String fullName =  createUserDto.getCreateUserDetailDto().getFullName();
            userDetail.setFullName(fullName);
        }

        User user = new User(userId, createUserDto.getUsername(), createUserDto.getPassword(), userDetail);

        return user;
    }

    private UserDto mapToDto(User user) {
        UserDetailDto userDetailDto = new UserDetailDto(user.getId(), user.getUserDetail().getFullName());

        return new UserDto(user.getId(), user.getUsername(), user.getPassword());
    }
}
