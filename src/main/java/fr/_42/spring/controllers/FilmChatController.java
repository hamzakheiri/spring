package fr._42.spring.controllers;

import fr._42.spring.models.ChatMessage;
import fr._42.spring.models.UserSession;
import fr._42.spring.services.ChatMessagesService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
//    @ResponseBody
    public String showChatPage(
            @PathVariable("filmId") Long filmId,
            @CookieValue(value = "userId", defaultValue = "") String userIp,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info("Accessing chat page for film ID: {}", filmId);

        if (userIp.isEmpty()) {
            userIp = request.getRemoteAddr();
            response.addCookie(new Cookie("userId", userIp));
        }

        // Add filmId to the model so it can be accessed in the template
        model.addAttribute("filmId", filmId);

        List<ChatMessage> messages = new ArrayList<>();
        List<UserSession> userSessions = new ArrayList<>();
//        return filmId + " " + userIp;
        return "users/filmChat";
    }

    @MessageMapping("/films/{filmId}/chat/send")
    @SendTo("/topic/films/{filmId}/chat/messages")
    public ChatMessage handleChatMessage(@DestinationVariable("filmId") Long filmId, ChatMessage message) {
        try {
            logger.info("Chat message - Film ID: {}, Sender ID: {}, Content: '{}'",
                    filmId, message.getSenderId(), message.getContent());

            message.setFilmId(filmId);

            if (message.getTimestamp() == null) {
                message.setTimestamp(LocalDateTime.now());
            }
            ChatMessage chatMessage = chatMessagesService.addMessage(message);
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
