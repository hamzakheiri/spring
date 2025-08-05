package fr._42.spring.repositories;

import fr._42.spring.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionsRepository extends JpaRepository<Session, Long> {
    List<Session> findByFilm_TitleContainingIgnoreCase(String filmName);
}
