package fr._42.spring.controllers;


import fr._42.spring.models.User;
import fr._42.spring.services.AccountConfirmationsService;
import fr._42.spring.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/confirm")
public class UserConfirmationController {

    private static final Logger logger = LoggerFactory.getLogger(UserConfirmationController.class);
    private final AccountConfirmationsService accountConfirmationsService;
    public UserConfirmationController(
            UsersService usersService,
            AccountConfirmationsService accountConfirmationsService
    ) {
        this.accountConfirmationsService = accountConfirmationsService;
    }

    @GetMapping("/{confirmationCode}")
    public String showConfirmationForm(
            @PathVariable("confirmationCode") String confirmationCode,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        try {
            accountConfirmationsService.isConfirmationCodeValid(confirmationCode);
            model.addAttribute("confirmationCode", confirmationCode);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signin";
        }
        return "users/confirm";
    }

    @PatchMapping("/{confirmationCode}")
    @ResponseBody
    public String confirmUser(
            @PathVariable("confirmationCode") String confirmationCode,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        logger.info("Received confirmation request for code: {}, email: {}", confirmationCode, email);

        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            logger.info("Attempting to confirm user with email: {}", email);
            accountConfirmationsService.confirmUser(confirmationCode, user);
            logger.info("User confirmed successfully!");

            return "Account confirmed successfully!";
        } catch (IllegalArgumentException e) {
            logger.error("Confirmation failed: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

}
