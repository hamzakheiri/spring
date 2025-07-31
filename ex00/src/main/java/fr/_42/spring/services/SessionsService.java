package fr._42.spring.services;

import fr._42.spring.models.Session;
import fr._42.spring.repositories.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionsService {
    private final SessionsRepository sessionsRepository;

    @Autowired
    public SessionsService(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    public List<Session> getSessions() {
        return sessionsRepository.findAll();
    }

    public Optional<Session> getSessionById(Long id) {
        return sessionsRepository.findById(id);
    }

    public Session addSession(Session session) {
        return sessionsRepository.save(session);
    }

    public List<Session> muchThePatternFilmTitle(String filmTilePattern) {
        return sessionsRepository.findByFilm_TitleContainingIgnoreCase(filmTilePattern);
    }
}
