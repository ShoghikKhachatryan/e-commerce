package com.example.e_commerce.service;

import com.example.e_commerce.entity.User;
import com.example.e_commerce.entity.UserDetail;
import com.example.e_commerce.repository.UserDetailRepository;
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
class UserDetailServiceTest {

    @Mock
    private UserDetailRepository mockedUserDetailRepository;

    @InjectMocks
    private UserDetailService mockedUserDetailService;

    private UserDetail userDetail;

    @BeforeEach
    void setUp() {
        userDetail = new UserDetail();
        userDetail.setFullName("Leo Deo");
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedUserDetailRepository);
    }

    @Test
    void createUserDetail() {
        when(mockedUserDetailRepository.save(userDetail)).thenReturn(userDetail);

        mockedUserDetailService.createUserDetail(userDetail);

        verify(mockedUserDetailRepository).save(userDetail);
    }

    @Test
    void deleteUserDetailById() {
        UUID id = userDetail.getId();
        doNothing().when(mockedUserDetailRepository).deleteById(id);

        mockedUserDetailService.deleteUserDetailById(id);

        verify(mockedUserDetailRepository).deleteById(id);
    }

    @Test
    void getUserDetail() {
        UUID id = userDetail.getId();
        when(mockedUserDetailRepository.findById(id)).thenReturn(Optional.of(userDetail));

        mockedUserDetailService.getUserDetail(id);

        verify(mockedUserDetailRepository).findById(id);
    }

    @Test
    void getUsers() {
        when(mockedUserDetailRepository.findAll()).thenReturn(List.of(userDetail));

        mockedUserDetailService.getUsers();

        verify(mockedUserDetailRepository).findAll();
    }

    @Test
    void updateUserDetail() {
        when(mockedUserDetailRepository.save(userDetail)).thenReturn(userDetail);

        mockedUserDetailService.updateUserDetail(userDetail);

        verify(mockedUserDetailRepository).save(userDetail);
    }
}