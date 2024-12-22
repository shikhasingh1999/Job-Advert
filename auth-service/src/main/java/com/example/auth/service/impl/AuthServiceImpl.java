package com.example.auth.service.impl;

import com.example.auth.client.UserServiceClient;
import com.example.auth.dto.RegisterDto;
import com.example.auth.dto.TokenDto;
import com.example.auth.model.LoginRequest;
import com.example.auth.model.RegisterRequest;
import com.example.auth.service.AuthService;
import com.example.auth.service.impl.common.JwtService;
import com.example.common.exception.WrongCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceClient userServiceClient;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthServiceImpl(UserServiceClient userServiceClient, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userServiceClient = userServiceClient;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public RegisterDto register (RegisterRequest request) {
        return userServiceClient.save(request).getBody();
    }

    @Override
    public TokenDto login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return TokenDto
                    .builder()
                    .token(jwtService.generateToken(request.getUsername()))
                    .build();
        }
        else {
            throw new WrongCredentialsException("Wrong Credentials");
        }
    }
}
