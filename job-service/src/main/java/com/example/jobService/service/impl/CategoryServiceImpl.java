package com.example.jobService.service.impl;

import com.example.common.entity.Category;
import com.example.common.repository.CategoryRepository;
import com.example.jobService.client.FileStorageClient;
import com.example.jobService.exception.NotFoundException;
import com.example.jobService.model.category.CategoryCreateRequest;
import com.example.jobService.model.category.CategoryUpdateRequest;
import com.example.jobService.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;

    @Override
    public Category getCategoryById (String id) {
        return findCategoryById(id);
    }

    @Override
    public Category createCategory(CategoryCreateRequest request, MultipartFile file) {
        String imageId = null;

        if (file != null) {
            imageId = fileStorageClient.uploadImageToFileSystem(file).getBody();
        }

        return categoryRepository.save(Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageId(imageId)
                .build());
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategoryById(CategoryUpdateRequest request, MultipartFile file) {
        Category categoryToUpdate = findCategoryById(request.getId());
        modelMapper.map(request, categoryToUpdate);

        if (file != null) {
            String imageId = fileStorageClient.uploadImageToFileSystem(file).getBody();
            if (imageId != null) {
                fileStorageClient.deleteImageFromFileSystem(categoryToUpdate.getImageId());
                categoryToUpdate.setImageId(imageId);
            }
        }

        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }

    private Category findCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
