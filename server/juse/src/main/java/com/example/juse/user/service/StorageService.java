package com.example.juse.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class StorageService {

    private final Path rootLocation = Paths.get("C:\\images");

    public String store(MultipartFile file){

        String savedName = uploadFile(file.getOriginalFilename());

        try {
            Path destinationFile = this.rootLocation.resolve(
                    savedName).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory");
            }

            try (InputStream inputStream = file.getInputStream()) {
                log.info("# store User Image!");
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return savedName;
    }

    private String uploadFile(String originalName) {
        // uuid 생성
        UUID uuid = UUID.randomUUID();

        String savedName = uuid.toString() + "_" + originalName;

        return savedName;
    }
}
