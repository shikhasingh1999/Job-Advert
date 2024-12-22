package com.example.userService.dto;

import com.example.common.model.UserDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String id;
    private String username;
    private String email;
    private UserDetails userDetails;
}

