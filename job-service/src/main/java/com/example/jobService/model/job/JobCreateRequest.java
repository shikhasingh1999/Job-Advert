package com.example.jobService.model.job;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobCreateRequest {

    @NotBlank(message = "Job name is required")
    private String name;
    private String description;
    @NotBlank(message = "Category id is required")
    private String categoryId;
    private String[] keys;
}
