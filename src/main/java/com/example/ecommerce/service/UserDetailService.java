package com.example.ecommerce.service;

import com.example.ecommerce.entity.UserDetail;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.UserDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;

    public UserDetailService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    public UserDetail getUserDetail(UUID id) {
        return userDetailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public List<UserDetail> getAllUserDetails() {
        return userDetailRepository.findAll();
    }

    public void updateUserDetail(UserDetail userDetail) {
        getUserDetail(userDetail.getId());
        userDetailRepository.save(userDetail);
    }
}
