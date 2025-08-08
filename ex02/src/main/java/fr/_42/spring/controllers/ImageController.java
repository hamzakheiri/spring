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

    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> serveImage(@PathVariable("filename") String filename) {
        try {
            // Security: Validate filename to prevent directory traversal
            if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
                logger.warn("Potential directory traversal attempt with filename: {}", filename);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Validate filename is not empty
            if (filename.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Path uploadDir = Paths.get(imageDir);
            Path file = uploadDir.resolve(filename);

            // Security: Ensure the resolved path is still within the upload directory
            if (!file.startsWith(uploadDir)) {
                logger.warn("Attempted access outside upload directory: {}", file);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Check if file exists
            if (!Files.exists(file)) {
                logger.debug("Image file not found: {}", filename);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            byte[] image = Files.readAllBytes(file);

            String mimeType = Files.probeContentType(file);
            MediaType mediaType = mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.APPLICATION_OCTET_STREAM;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.setCacheControl("public, max-age=31536000"); // Cache for 1 year

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("Error serving image: " + filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
