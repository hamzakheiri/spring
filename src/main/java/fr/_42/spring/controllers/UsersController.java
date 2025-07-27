package fr._42.spring.controllers;

import fr._42.spring.models.Role;
import fr._42.spring.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Authentication authentication) {
        // If user is already authenticated, redirect to profile
        if (authentication != null && authentication.isAuthenticated() &&
            !authentication.getName().equals("anonymousUser")) {
            return "redirect:/profile";
        }
        return "users/signup";
    }

    @PostMapping("/signup")
    public String handleSignUpForm(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) MultipartFile avatarFile,
            RedirectAttributes redirectAttributes
    ) {
        try {
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Passwords do not match");
                return "redirect:/signup";
            }
            //ToDo handle the avatar later

            usersService.createUser(firstName, lastName, password, email, phoneNumber, Role.USER, null);
            redirectAttributes.addFlashAttribute("success", "Account created successfully! Welcome " + firstName + "!");
            return "redirect:/signin";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        }
    }

    @GetMapping("/signin")
    public String showSignInForm(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() &&
            !authentication.getName().equals("anonymousUser")) {
            return "redirect:/profile";
        }
        return "users/signin";
    }
}
