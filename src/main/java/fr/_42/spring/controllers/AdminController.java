package fr._42.spring.controllers;

import fr._42.spring.models.Hall;
import fr._42.spring.services.HallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/admin/panel")
public class AdminController {
    private final HallsService hallsService;

    @Autowired
    public AdminController(HallsService hallsService) {
        this.hallsService = hallsService;
    }

    @GetMapping(value = {"/halls", "/halls/"})
    public String showHallsPanel(
            RedirectAttributes redirectAttributes,
            Model model) {
        try {
            List<Hall> halls = hallsService.getAllHalls();
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

    @GetMapping(value = {"/films", "/films/"})
    @ResponseBody()
    public String showFilmsPanel() {
        return "admin/films";
    }

    @GetMapping("/sessions")
    @ResponseBody()
    public String showSessionsPanel() {
        return "admin/sessions";
    }
}
