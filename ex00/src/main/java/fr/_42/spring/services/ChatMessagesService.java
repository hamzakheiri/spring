package fr._42.spring.services;

import fr._42.spring.models.ChatMessage;
import fr._42.spring.repositories.ChatMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessagesService {
    ChatMessagesRepository chatMessagesRepository;
    UsersService usersService;

    @Autowired
    public ChatMessagesService(ChatMessagesRepository chatMessagesRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
        this.usersService = usersService;
    }

    public ChatMessage addMessage(ChatMessage message) {
        if (message.getSenderFirstName() == null) {
            message.setSenderFirstName(usersService.getUserById(message.getSenderId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"))
                    .getFirstName());
        }
        return chatMessagesRepository.save(message);
    }

    public List<ChatMessage> getLatestMessagesByFilmId(Long filmId) {
        return chatMessagesRepository.findLatest20MessagesByFilmId(filmId);
    }
}
