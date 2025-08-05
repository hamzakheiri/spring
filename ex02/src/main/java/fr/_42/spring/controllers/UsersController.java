package fr._42.spring.controllers;

import fr._42.spring.models.AccountConfirmation;
import fr._42.spring.models.Role;
import fr._42.spring.models.User;
import fr._42.spring.services.AccountConfirmationsService;
import fr._42.spring.services.EmailService;
import fr._42.spring.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;
    private final EmailService emailService;
    private final AccountConfirmationsService accountConfirmationsService;

    @Autowired
    public UsersController(
            UsersService usersService,
            EmailService emailService,
            AccountConfirmationsService accountConfirmationsService
    ) {
        this.emailService = emailService;
        this.usersService = usersService;
        this.accountConfirmationsService = accountConfirmationsService;
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
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                logger.info("Validation error: " + error.toString());
            });

            model.addAttribute("user", user);
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("error", "Please correct the errors below");
            return "users/signup";
        }

        try {
            if (!user.getPassword().equals(confirmPassword)) {
                model.addAttribute("user", user);
                model.addAttribute("passwordMatchError", "Passwords do not match");
                return "users/signup";
            }
            //ToDo: handle the avatar later

            User createdUser = usersService.createUser(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    Role.USER,
                    null
            );
            AccountConfirmation accountConfirmation = accountConfirmationsService.createConfirmation(createdUser.getId(), createdUser.getEmail());
            redirectAttributes.addFlashAttribute("success", "Please confirm the account through the email sent to " + user.getEmail());
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

    @GetMapping("/testmail")
    @ResponseBody
    public String showUsersList() {
        try {
            emailService.sendEmail("hamza.kheiri@gmail.com", "Test", "Test");
        } catch (MailException e) {
            logger.error("Error sending email: {}", e.getMessage());
            return "Error sending email: " + e.getMessage();
        }
        return "mail sent successfully!";
    }


}
