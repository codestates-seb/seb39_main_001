package com.example.juse.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class StorageService {

    private final Path rootLocation = Paths.get("C:\\images");

    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Empty file");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
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
    }
}
