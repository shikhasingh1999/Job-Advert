package com.example.jobService.model.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryUpdateRequest {

    @NotBlank(message = "Category id is required")
    private String id;
    private String name;
    private String description;
}
