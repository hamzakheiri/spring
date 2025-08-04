package fr._42.spring.controllers;

import fr._42.spring.models.Role;
import fr._42.spring.models.User;
import fr._42.spring.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model, Authentication authentication) {
        // If user is already authenticated, redirect to profile
        if (authentication != null && authentication.isAuthenticated() &&
            !authentication.getName().equals("anonymousUser")) {
            return "redirect:/profile";
        }

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "users/signup";
    }

    @PostMapping("/signup")
    public String handleSignUpForm(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) MultipartFile avatarFile,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        // Log validation errors for debugging
        logger.info("Validation result: " + bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                logger.info("Validation error: " + error.toString());
            });

            // Add user back to model to preserve entered values
            model.addAttribute("user", user);
            model.addAttribute("bindingResult", bindingResult);
            // Add form submission error
            model.addAttribute("error", "Please correct the errors below");
            return "users/signup";
        }

        try {
            if (!user.getPassword().equals(confirmPassword)) {
                model.addAttribute("user", user);
                model.addAttribute("passwordMatchError", "Passwords do not match");
                return "users/signup";
            }
            //ToDo handle the avatar later

            usersService.createUser(
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhoneNumber(),
                Role.USER,
                null
            );
            redirectAttributes.addFlashAttribute("success", "Account created successfully! Welcome " + user.getFirstName() + "!");
            return "redirect:/signin";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "users/signup";
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
