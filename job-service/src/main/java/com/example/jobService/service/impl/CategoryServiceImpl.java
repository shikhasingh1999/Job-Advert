package com.example.jobService.service.impl;

import com.example.common.entity.Category;
import com.example.common.repository.CategoryRepository;
import com.example.jobService.service.CategoryService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryById (String id) {
        return findCategoryById(id);
    }

    private Category findCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
