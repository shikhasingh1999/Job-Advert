package com.example.fileStorage.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "files")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    private String id;
    private String type;
    private String filePath;
}
