package edu.mx.unsis.unsiSmile.service.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.exceptions.AppException;

@Service
public class FileStorageService {

    public String storeFile(MultipartFile file) {
        try {
            Path uploadDir = Paths.get(Constants.UPLOAD_PATH);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return fileName;
        } catch (IOException e) {
            throw new AppException("Could not store file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public byte[] loadFile(String fileName) {
        try {
            Path filePath = Paths.get(Constants.UPLOAD_PATH).resolve(fileName);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new AppException("Could not read file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(Constants.UPLOAD_PATH).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new AppException("Could not delete file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}