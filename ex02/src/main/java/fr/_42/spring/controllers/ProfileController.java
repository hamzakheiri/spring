package fr._42.spring.controllers;

import fr._42.spring.models.User;
import fr._42.spring.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ProfileController {
    @PreAuthorize("@customSecurity.isConfirmed(authentication)")
    @GetMapping("/profile")
    public String showProfile(
            Authentication authentication,
            HttpSession session,
            Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            try {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                user = userDetails.getUser();
                session.setAttribute("user", user);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to retrieve user details");
            }
        }

        model.addAttribute("user", user);
        return "/users/profile";
    }
}