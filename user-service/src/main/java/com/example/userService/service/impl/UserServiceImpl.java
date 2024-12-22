package com.example.userService.service.impl;

import com.example.common.entity.User;
import com.example.common.enums.Active;
import com.example.common.enums.Role;
import com.example.common.repository.UserRepository;
import com.example.userService.model.RegisterRequest;
import com.example.userService.service.UserService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.userService.constants.Constant.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(RegisterRequest request) {
        User toSave = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .active(Active.ACTIVE)
                .build();
        return userRepository.save(toSave);
    }

    @Override
    public User getUserById(String id) {
        return findUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return findUserByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return findUserByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAllByActive(Active.ACTIVE);
    }

    private User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    private User findUserByUsername (String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
