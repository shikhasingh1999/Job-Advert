package com.example.userService.service;

import com.example.common.entity.User;
import com.example.userService.model.RegisterRequest;
import com.example.userService.model.UpdateUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {

    User saveUser(RegisterRequest request);

    User getUserById(String id);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    List<User> getAll();

    User updateUserById(UpdateUserRequest userRequest, MultipartFile file);
}
