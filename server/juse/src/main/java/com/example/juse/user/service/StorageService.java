package com.example.juse.user.service;

import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StorageService {

    private final Path rootLocation = Paths.get("C:\\images");
    private static final Tika tika = new Tika();

    public String store(MultipartFile file){

        String savedName = uploadFile(file.getOriginalFilename());

        Path destinationFile = this.rootLocation.resolve(savedName).normalize().toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {

//            boolean isValid = validImgFile(inputStream);
                System.out.println("inputStream.toString() = " + file.getContentType());

//                if(!isValid) {
//                    throw new CustomRuntimeException(ExceptionCode.NOT_VALID_IMAGE_TYPE);
//                }

                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

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

    public boolean validImgFile(InputStream inputStream) {

        try {
            List<String> notValidTypeList = Arrays.asList("image/jpeg", "image/pjpeg", "image/png",
                    "image/gif", "image/bmp", "image/x-windows-bmp");

            String mimeType = tika.detect(inputStream);
            System.out.println("RealType = " + mimeType);

            boolean isValid = notValidTypeList.stream()
                    .anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));

            return isValid;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
