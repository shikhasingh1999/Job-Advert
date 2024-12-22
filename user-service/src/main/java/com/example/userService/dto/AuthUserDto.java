package com.example.userService.dto;

import com.example.common.enums.Role;
import lombok.Data;

@Data
public class AuthUserDto {

    private String id;
    private String username;
    private String password;
    private Role role;
}
