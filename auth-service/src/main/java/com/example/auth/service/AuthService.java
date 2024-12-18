package com.example.auth.service;

import com.example.auth.dto.RegisterDto;
import com.example.auth.dto.TokenDto;
import com.example.auth.model.LoginRequest;
import com.example.auth.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    RegisterDto register (RegisterRequest request);

    TokenDto login(LoginRequest loginRequest);
}
