package com.example.fileStorage.service.impl;

import com.example.fileStorage.repository.FileStorageRepository;
import com.example.fileStorage.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    @Override
    public String uploadImageToFileSystem(MultipartFile file) {
        return null;
    }
}
