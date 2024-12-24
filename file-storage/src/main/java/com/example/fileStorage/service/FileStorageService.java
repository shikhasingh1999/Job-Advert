package com.example.fileStorage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStorageService {
    String uploadImageToFileSystem(MultipartFile file);

    byte [] downloadImageFromFileSystem(String id);

    void deleteImageFromFileSystem(String id);
}
