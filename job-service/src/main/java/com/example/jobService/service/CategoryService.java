package com.example.jobService.service;

import com.example.common.entity.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    Category getCategoryById (String id);
}
