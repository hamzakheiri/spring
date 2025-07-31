package fr._42.spring.services;

import fr._42.spring.models.Film;
import fr._42.spring.repositories.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmsService {
    private final FilmsRepository filmsRepository;

    @Autowired
    public FilmsService(FilmsRepository filmsRepository) {
        this.filmsRepository = filmsRepository;
    }

    public void addFilm(Film film) {
        filmsRepository.save(film);
    }

    public List<Film> getFilms() {
        return filmsRepository.findAll();
    }

    public Film getFilmById(Long id) {
        return filmsRepository.findById(id).orElse(null);
    }
}