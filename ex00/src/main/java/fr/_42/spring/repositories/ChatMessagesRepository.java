package fr._42.spring.repositories;

import fr._42.spring.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT c FROM ChatMessage c WHERE c.filmId = :filmId ORDER BY c.timestamp DESC limit 20")
    List<ChatMessage> findLatest20MessagesByFilmId(Long filmId);
}
