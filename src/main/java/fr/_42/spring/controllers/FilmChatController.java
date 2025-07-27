package fr._42.spring.controllers;

import fr._42.spring.models.ChatMessage;
import fr._42.spring.models.User;
import fr._42.spring.security.CustomUserDetails;
import fr._42.spring.services.ChatMessagesService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/films/")
public class FilmChatController {
    private static final Logger logger = LoggerFactory.getLogger(FilmChatController.class);
    private final ChatMessagesService chatMessagesService;


    @Autowired
    public FilmChatController(ChatMessagesService chatMessagesService) {
        this.chatMessagesService = chatMessagesService;
    }

    @GetMapping(value = {"{filmId}/chat", "{filmId}/chat/"})
    public String showChatPage(
            @PathVariable("filmId") Long filmId,
            @CookieValue(value = "userId", defaultValue = "") String userIp,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/signin";
        }

        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        logger.info("Accessing chat page for film ID: {}", filmId);
        logger.info("The current user: {}", user);
        if (userIp.isEmpty()) {
            userIp = request.getRemoteAddr();
            response.addCookie(new Cookie("userId", userIp));
        }

        model.addAttribute("filmId", filmId);
        model.addAttribute("currentUser", user);
        model.addAttribute("currentUserId", user.getId());
        model.addAttribute("currentUsername", user.getFirstName());

        return "users/filmChat";
    }

    @MessageMapping("/films/{filmId}/chat/send")
    @SendTo("/topic/films/{filmId}/chat/messages")
    public ChatMessage handleChatMessage(
            @DestinationVariable("filmId") Long filmId,
            ChatMessage message,
            Authentication authentication
    ) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                logger.warn("Unauthenticated user tried to send message");
                return createErrorMessage("Authentication required", filmId);
            }
            User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

            message.setFilmId(filmId);
            message.setSenderId(user.getId());
            message.setSenderFirstName(user.getFirstName()); // Add this line to set the sender's first name

            if (message.getTimestamp() == null)
                message.setTimestamp(LocalDateTime.now());
            message = chatMessagesService.addMessage(message);
            return message;

        } catch (IllegalArgumentException e) {
            logger.error("Invalid message data: {}", e.getMessage());
            return createErrorMessage("Invalid message data", filmId);
        } catch (DataAccessException e) {
            logger.error("Database error while saving chat message: {}", e.getMessage());
            return createErrorMessage("Failed to save message", filmId);
        } catch (Exception e) {
            logger.error("Unexpected error handling chat message: {}", e.getMessage(), e);
            return createErrorMessage("Internal server error", filmId);
        }
    }

    private ChatMessage createErrorMessage(String errorText, Long filmId) {
        ChatMessage errorMessage = new ChatMessage();
        errorMessage.setFilmId(filmId);
        errorMessage.setSenderId(-999L); // Special ID to identify system error messages
        errorMessage.setContent("ERROR: " + errorText);
        errorMessage.setTimestamp(LocalDateTime.now());
        return errorMessage;
    }
}