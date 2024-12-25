package com.example.jobService.service;

import com.example.common.entity.Category;
import com.example.common.entity.Job;
import com.example.jobService.model.category.CategoryCreateRequest;
import com.example.jobService.model.category.CategoryUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Service
public interface CategoryService {

    Category getCategoryById (String id);

    Category createCategory(CategoryCreateRequest request, MultipartFile file);

    List<Category> getAll();

    Category updateCategoryById(CategoryUpdateRequest request, MultipartFile file);

    void deleteCategoryById(String id);
}
