package fr._42.spring.services;

import fr._42.spring.models.Image;
import fr._42.spring.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService {
    private final ImagesRepository imagesRepository;

    @Autowired
    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public List<Image> getImages() {
        return imagesRepository.findAll();
    }

    public Image getImageByStoredFilename(String storedFilename) {
        return imagesRepository.findByStoredFilename(storedFilename).orElse(null);
    }

    public List<Image> getImagesByUserId(String userId) {
        return imagesRepository.findByUserId(userId);
    }
}
