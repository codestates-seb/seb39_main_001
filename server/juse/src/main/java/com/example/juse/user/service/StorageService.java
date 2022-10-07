package com.example.juse.user.service;

import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class StorageService {

    private final Path rootLocation = Paths.get("C:\\images");
    private final Path resizeLocation = Paths.get("C:\\images\\resize\\");
    private static final Tika tika = new Tika();

    public String store(MultipartFile file){

        String savedName = uploadFile(file.getOriginalFilename());

        String originFilename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        String formatName = originFilename.substring(originFilename.lastIndexOf(".") + 1).toLowerCase();

        Path destinationFile = this.rootLocation.resolve(savedName).normalize().toAbsolutePath();

        String resizePath = resizeLocation  + "\\" + savedName;
        System.out.println("resizePath = " + resizePath);

        try (InputStream inputStream = file.getInputStream()) {

            InputStream inputStream1 = file.getInputStream();

            System.out.println("업로드한 확장자 타입 : " + file.getContentType());
            boolean isValid = validImgFile(inputStream);

                if(!isValid) {
                    System.out.println("이미지 파일만 업로드 가능합니다.");
                    throw new CustomRuntimeException(ExceptionCode.NOT_VALID_IMAGE_TYPE);
                }

                Files.copy(inputStream1, destinationFile, StandardCopyOption.REPLACE_EXISTING);

                imageResize(file, resizePath, formatName);

            } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
        return savedName;
    }

    /*
    * 파일 업로드 중복방지를 위한 파일 이름에 UUID 추가
     */
    private String uploadFile(String originalName) {
        // uuid 생성
        UUID uuid = UUID.randomUUID();

        String savedName = uuid.toString() + "_" + originalName;

        return savedName;
    }

    /*
    * 이미지파일만 업로드 가능하도록 파일 확장자 검사
     */
    public boolean validImgFile(InputStream inputStream) {

        try {
            List<String> notValidTypeList = Arrays.asList("image/jpeg", "image/pjpeg", "image/png",
                    "image/gif", "image/bmp", "image/x-windows-bmp");

            String mimeType = tika.detect(inputStream);
            System.out.println("실제 확장자 타입 : " + mimeType);

            boolean isValid = notValidTypeList.stream()
                    .anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));

            return isValid;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void imageResize(MultipartFile file, String resizePath, String formatName) throws IOException {
        BufferedImage inputImage = ImageIO.read(file.getInputStream());

        int originWidth = inputImage.getWidth();
        System.out.println("originWidth = " + originWidth);
        int originHeight = inputImage.getHeight();
        System.out.println("originHeight = " + originHeight);

        int newWidth = 500;

        if (originWidth > newWidth) {
            int newHeight = (originHeight * newWidth) / originWidth;

            Image resizeImage = inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = newImage.getGraphics();
            graphics.drawImage(resizeImage, 0, 0, null);
            graphics.dispose();

            File newFile = new File(resizePath);
            ImageIO.write(newImage, formatName, newFile);
        }
        else {
            file.transferTo(new java.io.File(resizePath));
        }

    }
}
