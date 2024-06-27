package com.example.ecommerce.service;

import com.example.ecommerce.dto.user.CreateUserDto;
import com.example.ecommerce.dto.userDetail.CreateUserDetailDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.exception.EntityByGivenNameExistException;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockedUserRepository;

    @InjectMocks
    private UserService mockedUserService;

    private final UUID userId = UUID.fromString("08617a12-c5ab-4910-a9e6-8d50fe64ab3e");

    private final User user = new User(userId, "Leo21", "1234!", new UserDetail(userId));

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedUserRepository);
    }

    @Test
    void createUser() {
        String username = user.getUsername();

        CreateUserDto createUserDto = new CreateUserDto(username, user.getPassword(), null);

        when(mockedUserRepository.existsByUsername(username)).thenReturn(false);
        when(mockedUserRepository.save(any(User.class))).thenReturn(user);

        mockedUserService.createUser(createUserDto);

        verify(mockedUserRepository).existsByUsername(username);
        verify(mockedUserRepository).save(any(User.class));
    }

    @Test
    void createUserWithUserDetail() {
        User user = new User(userId, "Leo21", "1234!", null);
        String username = user.getUsername();
        CreateUserDto createUserDto = new CreateUserDto(username, user.getPassword(), null);

        when(mockedUserRepository.existsByUsername(username)).thenReturn(false);
        when(mockedUserRepository.save(any(User.class))).thenReturn(user);

        mockedUserService.createUser(createUserDto);

        verify(mockedUserRepository).existsByUsername(username);
        verify(mockedUserRepository).save(any(User.class));
    }

    @Test
    void createUserWithCreateUserDetail() {
        String username = user.getUsername();

        CreateUserDto createUserDto = new CreateUserDto(username, user.getPassword(), new CreateUserDetailDto("Bob"));

        when(mockedUserRepository.existsByUsername(username)).thenReturn(false);
        when(mockedUserRepository.save(any(User.class))).thenReturn(user);

        mockedUserService.createUser(createUserDto);

        verify(mockedUserRepository).existsByUsername(username);
        verify(mockedUserRepository).save(any(User.class));
    }

    @Test
    void createUserByExitedName() {
        String username = user.getUsername();

        CreateUserDto createUserDto = new CreateUserDto(username, user.getPassword(), null);

        when(mockedUserRepository.existsByUsername(username)).thenReturn(true);

        Assertions.assertThrows(EntityByGivenNameExistException.class,
                () -> mockedUserService.createUser(createUserDto));

        verify(mockedUserRepository).existsByUsername(username);
    }

    @Test
    void deleteUserById() {
        when(mockedUserRepository.existsById(userId)).thenReturn(true);
        doNothing().when(mockedUserRepository).deleteById(userId);

        mockedUserService.deleteUserById(userId);

        verify(mockedUserRepository).existsById(userId);
        verify(mockedUserRepository).deleteById(userId);
    }

    @Test
    void deleteUserByNotExistedId() {
        when(mockedUserRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedUserService.deleteUserById(userId));

        verify(mockedUserRepository).existsById(userId);
    }

    @Test
    void getUserByExistedId() {
        when(mockedUserRepository.findById(userId)).thenReturn(Optional.of(user));

        mockedUserService.getUser(userId);

        verify(mockedUserRepository).findById(userId);
    }

    @Test
    void getUserByNotExistedId() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedUserService.getUser(userId));

        verify(mockedUserRepository).findById(userId);
    }

    @Test
    void getUsers() {
        when(mockedUserRepository.findAll()).thenReturn(List.of(user));

        mockedUserService.getUsers();

        verify(mockedUserRepository).findAll();
    }
}