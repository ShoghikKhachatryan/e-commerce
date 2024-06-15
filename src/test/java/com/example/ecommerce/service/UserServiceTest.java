package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockedUserRepository;

    @InjectMocks
    private UserService mockedUserService;

    private final User user = User.builder()
            .id(UUID.fromString("08617a12-c5ab-4910-a9e6-8d50fe64ab3e"))
            .username("Leo21")
            .password("1234!")
            .build();

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
    void getUserByExistedId() {
        UUID id = user.getId();
        when(mockedUserRepository.findById(id)).thenReturn(Optional.of(user));

        mockedUserService.getUser(id);

        verify(mockedUserRepository).findById(id);
    }

    @Test
    void getUserByNotExistedId() {
        UUID id = user.getId();
        Assertions.assertThrows(NoSuchElementException.class,
                () -> mockedUserService.getUser(id));

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
    void getAllUsers() {
        when(mockedUserRepository.findAll()).thenReturn(List.of(user));

        mockedUserService.getAllUsers();

        verify(mockedUserRepository).findAll();
    }

    @Test
    void updateExistedUser() {
        when(mockedUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mockedUserRepository.save(user)).thenReturn(user);

        mockedUserService.updateUser(user);

        verify(mockedUserRepository).findById(user.getId());
        verify(mockedUserRepository).save(user);
    }

    @Test
    void updateNotExistedUser() {
        Assertions.assertThrows(NoSuchElementException.class,
                () ->  mockedUserService.updateUser(user));

        verify(mockedUserRepository).findById(user.getId());
    }
}