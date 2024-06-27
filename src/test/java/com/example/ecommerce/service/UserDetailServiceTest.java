package com.example.ecommerce.service;

import com.example.ecommerce.dto.userDetail.UpdateUserDetailDto;
import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.UserDetailRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceTest {

    @Mock
    private UserDetailRepository mockedUserDetailRepository;

    @InjectMocks
    private UserDetailService mockedUserDetailService;

    private final UUID userId = UUID.fromString("08617a12-c5ab-4910-a9e6-8d50fe64ab3e");

    private final UserDetail userDetail = new UserDetail(userId, "Leo Deo");

    //private final UserDetailDto userDetailDto = new UserDetail(userId, "Leo Deo");

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedUserDetailRepository);
    }

    @Test
    void getUserDetailByExsitedId() {
        when(mockedUserDetailRepository.findById(userId)).thenReturn(Optional.of(userDetail));

        mockedUserDetailService.getUserDetail(userId);

        verify(mockedUserDetailRepository).findById(userId);
    }

    @Test
    void getUserDetailByNotExsitedId() {

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedUserDetailService.getUserDetail(userId));

        verify(mockedUserDetailRepository).findById(userId);
    }

    @Test
    void updateUserDetailByExsitedId() {
        String fullName = userDetail.getFullName();
        UpdateUserDetailDto updateUserDetailDto = new UpdateUserDetailDto(fullName);

        when(mockedUserDetailRepository.findById(userId)).thenReturn(Optional.of(userDetail));
        doNothing().when(mockedUserDetailRepository).updateFullName(userId, fullName);

        mockedUserDetailService.updateUserDetail(userId, updateUserDetailDto);

        verify(mockedUserDetailRepository).updateFullName(userId, fullName);
        verify(mockedUserDetailRepository).findById(userDetail.getId());
    }

    @Test
    void updateUserDetailByNotExsitedId() {
        UpdateUserDetailDto updateUserDetailDto = new UpdateUserDetailDto(userDetail.getFullName());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedUserDetailService.updateUserDetail(userId, updateUserDetailDto));

        verify(mockedUserDetailRepository).findById(userId);
    }
}