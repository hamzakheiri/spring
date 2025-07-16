package fr._42.spring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    public UserSession() {
        this.loginTime = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
    }

    public UserSession(Long userId, String ipAddress) {
        this();
        this.userId = userId;
        this.ipAddress = ipAddress;
    }

}
