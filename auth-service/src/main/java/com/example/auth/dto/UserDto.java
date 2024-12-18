package com.example.auth.dto;

import com.example.auth.enums.Role;
import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String username;
    private String password;
    private Role role;
}
