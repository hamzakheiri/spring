package fr._42.spring.controllers;

import fr._42.spring.models.Film;
import fr._42.spring.models.Hall;
import fr._42.spring.models.Session;
import fr._42.spring.services.FilmsService;
import fr._42.spring.services.HallsService;
import fr._42.spring.services.SessionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/admin/panel")
public class AdminController {
    private final HallsService hallsService;
    private final FilmsService filmsService;
    private final SessionsService sessionsService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    // todo: handle all the data validation
    @Autowired
    public AdminController(
            HallsService hallsService,
            FilmsService filmsService,
            SessionsService sessionsService
    ) {
        this.filmsService = filmsService;
        this.hallsService = hallsService;
        this.sessionsService = sessionsService;
    }

    @GetMapping(value = {"/halls", "/halls/"})
    public String showHallsPanel(
            RedirectAttributes redirectAttributes,
            Model model) {
        try {
            List<Hall> halls = hallsService.getHalls();
            model.addAttribute("halls", halls);
            return "admin/halls";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "admin/halls";
    }


    @PostMapping(value = {"/halls", "/halls/"})
    public String handleHallsForm(
            @RequestParam String serialNumber,
            @RequestParam int seats,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Hall hall = hallsService.createHall(serialNumber, seats);
            redirectAttributes.addFlashAttribute("success", "Hall created successfully!");
            return "redirect:/admin/panel/halls";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/panel/halls";
        }
    }


    // handling films actions

    @GetMapping(value = {"/films", "/films/"})
    public String showFilmsPanel(
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        try {
            List<Film> films = filmsService.getFilms();
            model.addAttribute("films", films);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("films", new ArrayList<>());
        }
        return "admin/films";
    }

    @PostMapping(value = {"/films", "/films/"})
    public String handleFilmsForm(
            @RequestParam("title") String title,
            @RequestParam("year") int year,
            @RequestParam("ageRestrictions") int ageRestrictions,
            @RequestParam("description") String description,
            @RequestParam("poster") MultipartFile posterUrl,
            RedirectAttributes redirectAttributes
    ) {
        try {
            //Todo: handle the poster
            Film film = new Film(null, title, year, ageRestrictions, description, null);
            filmsService.addFilm(film);
            redirectAttributes.addFlashAttribute("success", "Film created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/panel/films";
    }


    // handling the session actions
    @GetMapping(value = {"/sessions", "/sessions/"})
    public String showSessionsPanel(
            Model model) {
        try {
            List<Session> sessions = sessionsService.getSessions();
            List<Hall> halls = hallsService.getHalls();
            List<Film> films = filmsService.getFilms();

            model.addAttribute("halls", halls);
            model.addAttribute("films", films);
            model.addAttribute("sessions", sessions);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("halls", new ArrayList<>());
            model.addAttribute("films", new ArrayList<>());
            model.addAttribute("sessions", new ArrayList<>());
        }
        return "admin/sessions";
    }

    // todo: handle the validation
    @PostMapping(value = {"/sessions", "/sessions/"})
    public String handleSessionsForm(
            @RequestParam("filmId") Long filmId,
            @RequestParam("hallId") Long hallId,
            @RequestParam("sessionTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime sessionTime,
            @RequestParam("ticketPrice") Double ticketPrice,
            RedirectAttributes redirectAttributes
    ) {
        if (filmId == null || hallId == null || sessionTime == null || ticketPrice == null) {
            redirectAttributes.addFlashAttribute("error", "Please fill in all required fields.");
            return "redirect:/admin/panel/sessions";
        }

        try {
            Film film = filmsService.getFilmById(filmId);
            Hall hall = hallsService.getHallById(hallId);

            Session session = new Session(null, ticketPrice, sessionTime, film, hall);
            sessionsService.addSession(session);

            List<Session> updatedSessions = sessionsService.getSessions();
            redirectAttributes.addFlashAttribute("sessions", updatedSessions);
            redirectAttributes.addFlashAttribute("success", "Session created successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "An error occurred while saving the session into the database");
        }

        return "redirect:/admin/panel/sessions";
    }
}