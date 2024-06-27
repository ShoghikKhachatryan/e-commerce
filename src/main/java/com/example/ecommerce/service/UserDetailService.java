package com.example.ecommerce.service;

import com.example.ecommerce.dto.userDetail.UpdateUserDetailDto;
import com.example.ecommerce.dto.userDetail.UserDetailDto;
import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.UserDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;

    public UserDetailService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    public UserDetailDto getUserDetail(UUID id) {
        UserDetail userDetail = userDetailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return mapToDto(userDetail);
    }

    @Transactional
    public UserDetailDto updateUserDetail(UUID id, UpdateUserDetailDto updateUserDetailDto) {
        UserDetailDto userDetailDto = getUserDetail(id);

        String fullName = updateUserDetailDto.getFullName();
        userDetailRepository.updateFullName(id, fullName);

        userDetailDto.setFullName(fullName);

        return userDetailDto;
    }

    private UserDetailDto mapToDto(UserDetail userDetail) {
        return new UserDetailDto(userDetail.getId(), userDetail.getFullName());
    }
}
