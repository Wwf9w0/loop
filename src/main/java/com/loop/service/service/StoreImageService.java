package com.loop.service.service;

import com.loop.service.advice.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class StoreImageService {


    public String storePath(MultipartFile file, Path path, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName, 2002);
            }
            if (fileName.contains("..")) {
                throw new StorageException("Cannot store file with relative path " + fileName, 2002);
            }

            try (InputStream inputStream = file.getInputStream()) {
                if (!path.toFile().exists()) {
                    Files.createDirectories(path);
                }
                Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }
        return path.resolve(fileName).toString();
    }
}
