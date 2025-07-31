package fr._42.spring.controllers;

import fr._42.spring.services.SessionsService;
import fr._42.spring.models.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/session")
public class LiveSessionController {
    private final SessionsService sessionsService;
    private static final Logger logger = LoggerFactory.getLogger(LiveSessionController.class);

    @Autowired
    public LiveSessionController(SessionsService sessionsService) {
        this.sessionsService = sessionsService;
    }

    @GetMapping(value = {"/search", "/search/"})
    public String showSearchPage() {
        return "users/liveSessionSearch";
    }

    @GetMapping(value = {"/search", "/search/"}, params = "query")
    @ResponseBody
    public List<Session> handleSearchSessionsQuery(@RequestParam("query") String query) {
        return sessionsService.muchThePatternFilmTitle(query);
    }

    @GetMapping(value = {"/{sessionId}", "/{sessionId}/"})
    public String showSessionPage(
            @PathVariable("sessionId") Long sessionId,
            Model model) {
        try {
            Session session = sessionsService.getSessionById(sessionId).orElse(null);
            logger.info("Session: {}", session);
            model.addAttribute("session", session);
            if (session == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Session not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to retrieve session details");
        }
        return "users/sessionDetails";
    }


}
