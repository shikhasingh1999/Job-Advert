package com.example.userService.controller;

import com.example.userService.dto.UserDto;
import com.example.userService.model.RegisterRequest;
import com.example.userService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<UserDto> save (@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(modelMapper.map(userService.saveUser(request), UserDto.class));
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById (@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail (@PathVariable String email) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserByEmail(email), UserDto.class));
    }
}
