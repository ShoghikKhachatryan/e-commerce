package com.example.e_commerce.service;

import com.example.e_commerce.entity.UserDetail;
import com.example.e_commerce.repository.UserDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;

    public UserDetailService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    public void createUserDetail(UserDetail userDetail) {
        userDetailRepository.save(userDetail);
    }

    public void deleteUserDetailById(UUID id) {
        userDetailRepository.deleteById(id);
    }

    public UserDetail getUserDetail(UUID id) {
        return userDetailRepository.findById(id).get();
    }

    public List<UserDetail> getUsers() {
        return userDetailRepository.findAll();
    }

    public void updateUserDetail(UserDetail userDetail) {
        userDetailRepository.save(userDetail);
    }
}
