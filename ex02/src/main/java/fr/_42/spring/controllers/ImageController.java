package fr._42.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Value("${app.upload.path}")
    private String imageDir;

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @PreAuthorize("@customSecurity.isConfirmed(authentication)")
    @GetMapping("/{filename:.+}")
    ResponseEntity<byte[]> serveImage(@PathVariable("filename") String filename) {
        try {
            Path file = Paths.get(imageDir).resolve(filename);
            byte[] image = Files.readAllBytes(file);

            String mimeType = Files.probeContentType(file);
            MediaType mediaType = mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.APPLICATION_OCTET_STREAM;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("Error serving image: " + filename, e); // Log the exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
