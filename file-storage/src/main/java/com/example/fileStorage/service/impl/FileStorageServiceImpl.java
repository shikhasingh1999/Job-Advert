package com.example.fileStorage.service.impl;

import com.example.fileStorage.exception.GenericErrorResponse;
import com.example.fileStorage.repository.FileStorageRepository;
import com.example.fileStorage.service.FileStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private String FOLDER_PATH;

    @PostConstruct
    public void init () {
        String currentWorkingDirectory = System.getProperty("user.dir");
        FOLDER_PATH = currentWorkingDirectory + "/file-storage/src/main/resources/attachments";
        File targetFolder = new File(FOLDER_PATH);

        // if target folder does not exist, then create a new folder
        if (!targetFolder.exists()) {
            boolean directoriesCreated = targetFolder.mkdirs();
            if (!directoriesCreated) {
                throw GenericErrorResponse.builder()
                        .message("Unable to create directories")
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }


        }
    }

    @Override
    public String uploadImageToFileSystem(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String filePath = FOLDER_PATH + "/" + uuid;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw GenericErrorResponse.builder()
                    .message("Unable to save file to storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        fileStorageRepository.save(com.example.fileStorage.entity.File.builder()
                .id(uuid)
                .type(file.getContentType())
                .filePath(filePath)
                .build());

        return uuid;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String id) {
        try {
            return Files.readAllBytes(new File(findFileById(id).getFilePath()).toPath());
        } catch (IOException e) {
            throw GenericErrorResponse.builder()
                    .message("Unable to read file from storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public void deleteImageFromFileSystem(String id) {
        File file = new File(findFileById(id).getFilePath());

        boolean deletionResult = file.delete();
        if (deletionResult) {
            fileStorageRepository.deleteById(id);
        }
        else {
            throw GenericErrorResponse.builder()
                    .message("Unable to delete file from storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    private com.example.fileStorage.entity.File findFileById(String id) {
        return fileStorageRepository.findById(id)
                .orElseThrow(() -> GenericErrorResponse.builder()
                        .message("File not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build());
    }
}
