package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.repository.UserDetailRepository;
import org.junit.jupiter.api.*;
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
class UserDetailServiceTest {

    @Mock
    private UserDetailRepository mockedUserDetailRepository;

    @InjectMocks
    private UserDetailService mockedUserDetailService;

    private final User user = User.builder()
            .id(UUID.fromString("08617a12-c5ab-4910-a9e6-8d50fe64ab3e"))
            .username("Leo21")
            .password("1234!")
            .build();

    private final UserDetail userDetail = new UserDetail(user.getId(), "Leo Deo", user);

    @BeforeEach
    void setUpCheck() {
        Assertions.assertEquals(user.getId(), userDetail.getId());
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedUserDetailRepository);
    }

    @Test
    void getUserDetailByExsitedId() {
        UUID userId = userDetail.getId();
        when(mockedUserDetailRepository.findById(userId)).thenReturn(Optional.of(userDetail));

        mockedUserDetailService.getUserDetail(userId);

        verify(mockedUserDetailRepository).findById(userId);
    }

    @Test
    void getUserDetailByNotExsitedId() {
        UUID userId = userDetail.getId();

        Assertions.assertThrows(NoSuchElementException.class,
                () -> mockedUserDetailService.getUserDetail(userId));

        verify(mockedUserDetailRepository).findById(userId);
    }

    @Test
    void getAllUserDetails() {
        when(mockedUserDetailRepository.findAll()).thenReturn(List.of(userDetail));

        mockedUserDetailService.getAllUserDetails();

        verify(mockedUserDetailRepository).findAll();
    }

    @Test
    void updateUserDetailByExsitedId() {
        when(mockedUserDetailRepository.save(userDetail)).thenReturn(userDetail);
        when(mockedUserDetailRepository.findById(userDetail.getId())).thenReturn(Optional.of(userDetail));

        mockedUserDetailService.updateUserDetail(userDetail);

        verify(mockedUserDetailRepository).save(userDetail);
        verify(mockedUserDetailRepository).findById(userDetail.getId());
    }

    @Test
    void updateUserDetailByNotExsitedId() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> mockedUserDetailService.updateUserDetail(userDetail));

        verify(mockedUserDetailRepository).findById(userDetail.getId());
    }
}