package com.example.e_commerce.service;

import com.example.e_commerce.entity.User;
import com.example.e_commerce.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockedUserRepository;

    @InjectMocks
    private UserService mockedUserService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("Leo21");
        user.setPassword("1234!");
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedUserRepository);
    }

    @Test
    void createUser() {
        when(mockedUserRepository.save(user)).thenReturn(user);

        mockedUserService.createUser(user);

        verify(mockedUserRepository).save(user);
    }

    @Test
    void deleteUserById() {
        UUID id = user.getId();
        doNothing().when(mockedUserRepository).deleteById(id);

        mockedUserService.deleteUserById(id);

        verify(mockedUserRepository).deleteById(id);
    }

    @Test
    void getUserById() {
        UUID id = user.getId();
        when(mockedUserRepository.findById(id)).thenReturn(Optional.of(user));

        mockedUserService.getUser(id);

        verify(mockedUserRepository).findById(id);
    }

    @Test
    void getUserByName() {
        String username = user.getUsername();
        when(mockedUserRepository.findUserByUsername(username)).thenReturn(user);

        mockedUserService.getUser(username);

        verify(mockedUserRepository).findUserByUsername(username);
    }

    @Test
    void getUsers() {
        when(mockedUserRepository.findAll()).thenReturn(List.of(user));

        mockedUserService.getUsers();

        verify(mockedUserRepository).findAll();
    }

    @Test
    void updateUser() {
        when(mockedUserRepository.save(user)).thenReturn(user);

        mockedUserService.updateUser(user);

        verify(mockedUserRepository).save(user);
    }
}