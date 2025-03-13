package com.javarush.lapkinu.dashboard.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${image.directory:src/main/resources/static/customers/}")
    private String imageDirectory;

    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path file = Paths.get(imageDirectory).resolve(imageName);
            File f = file.toFile();
            if (!f.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                MediaType contentType = determineContentType(file);

                if (contentType != null) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(contentType);

                    return new ResponseEntity<>(resource, headers, HttpStatus.OK);
                } else {
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_OCTET_STREAM) // Тип по умолчанию
                            .body(resource);
                }

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private MediaType determineContentType(Path file) {
        try {
            String contentType = Files.probeContentType(file);
            if (contentType != null) {
                return MediaType.parseMediaType(contentType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}