package fr._42.spring.repositories;

import fr._42.spring.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByStoredFilename(String storedFilename);

    List<Image> findByUserId(String userId);
}
