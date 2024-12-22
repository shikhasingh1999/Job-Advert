package com.example.userService.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 6, message = "Username must be at least 6 characters")
    private String username;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "", message = "Password must be at least 8 characters and contain at least 1 letter and 1 number")
    private String password;

    @Email(message = "Email should be valid")
    private String email;
}
