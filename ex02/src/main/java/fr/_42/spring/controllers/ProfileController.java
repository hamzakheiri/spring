package fr._42.spring.controllers;

import fr._42.spring.models.User;
import fr._42.spring.security.CustomUserDetails;
import fr._42.spring.services.UsersService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UsersService usersService;

    @PreAuthorize("@customSecurity.isConfirmed(authentication)")
    @GetMapping("/profile")
    public String showProfile(
            Authentication authentication,
            HttpSession session,
            Model model) {

        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();

            // Always fetch fresh user data from database to ensure we have latest avatar
            User user = usersService.getUserById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            logger.info("Profile page - User ID: {}, Avatar: '{}'", user.getId(), user.getAvatar());

            // Update session with fresh user data
            session.setAttribute("user", user);
            model.addAttribute("user", user);

            return "/users/profile";
        } catch (Exception e) {
            logger.error("Error loading profile: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to retrieve user details");
        }
    }
}