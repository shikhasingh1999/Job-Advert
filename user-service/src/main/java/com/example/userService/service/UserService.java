package com.example.userService.service;

import com.example.common.entity.User;
import com.example.userService.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User saveUser(RegisterRequest request);

    User getUserById(String id);

    User getUserByEmail(String email);
}
