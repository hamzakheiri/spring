package fr._42.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
public final class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "sender_first_name")
    private String senderFirstName;

    @Column(name = "user_ip")
    private String userIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessage(Long id, Long filmId, Long senderId, String content, String senderFirstName, String userIp, LocalDateTime timestamp) {
        this.id = id;
        this.filmId = filmId;
        this.senderId = senderId;
        this.content = content;
        this.senderFirstName = senderFirstName;
        this.userIp = userIp;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(filmId, that.filmId) && Objects.equals(senderId, that.senderId) && Objects.equals(content, that.content) && Objects.equals(senderFirstName, that.senderFirstName) && Objects.equals(userIp, that.userIp) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, senderId, content, senderFirstName, userIp, timestamp);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", senderFirstName='" + senderFirstName + '\'' +
                ", userIp='" + userIp + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
