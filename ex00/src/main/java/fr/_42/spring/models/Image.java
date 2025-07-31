package fr._42.spring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "stored_filename", nullable = false)
    private String storedFilename;

    @Column(name = "upload_time", nullable = false)
    private LocalDateTime uploadTime;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public Image() {
        this.uploadTime = LocalDateTime.now();
    }

    public Image(String originalFilename, String storedFilename, String userId) {
        this();
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.userId = userId;
    }

}
