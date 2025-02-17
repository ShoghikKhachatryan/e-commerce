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

    public List<UserDetailDto> getAllUserDetails() {
        return userDetailRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public UserDetailDto updateUserDetail(UpdateUserDetailDto updateUserDetailDto) {
        UUID id = updateUserDetailDto.getId();

        String fullName = updateUserDetailDto.getFullName();

        UserDetailDto userDetailDto = getUserDetail(id);

        userDetailRepository.updateFullName(id, fullName);

        userDetailDto.setFullName(fullName);

        return userDetailDto;
    }

    private UserDetailDto mapToDto(UserDetail userDetail) {
        return new UserDetailDto(userDetail.getId(), userDetail.getFullName());
    }
}
