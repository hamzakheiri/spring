package fr._42.spring.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Column(length = 64, nullable = false)
    private String username;

    @Id
    @Column(length = 64)
    private String series;

    @Column(length = 64, nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSeries() { return series; }
    public void setSeries(String series) { this.series = series; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Date getLastUsed() { return lastUsed; }
    public void setLastUsed(Date lastUsed) { this.lastUsed = lastUsed; }
}