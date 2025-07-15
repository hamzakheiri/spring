package fr._42.spring.controllers;

import fr._42.spring.services.SessionsService;
import fr._42.spring.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/session/search")
public class LiveSessionController {
    private final SessionsService sessionsService;


    // todo: add the basic auth
    @Autowired
    public LiveSessionController(SessionsService sessionsService) {
        this.sessionsService = sessionsService;
    }

    @GetMapping()
    public String showSearchPage() {
        return "users/liveSessionSearch";
    }

    @GetMapping(params = "query")
    @ResponseBody
    public List<Session> handleSearchSessionsQuery(@RequestParam("query") String query) {
        return sessionsService.muchThePatternFilmTitle(query);
    }

}
