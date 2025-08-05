package fr._42.spring.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "account_confirmation")
@Getter
@Setter
public class AccountConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "confirmation_code", nullable = false)
    private String confirmationCode;

    public AccountConfirmation(Long id, Long userId, String confirmationCode) {
        this.id = id;
        this.userId = userId;
        this.confirmationCode = confirmationCode;
    }

    public AccountConfirmation() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountConfirmation that = (AccountConfirmation) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(confirmationCode, that.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, confirmationCode);
    }

    @Override
    public String toString() {
        return "AccountConfirmation{" +
                "id=" + id +
                ", userId=" + userId +
                ", confirmationCode=" + confirmationCode +
                '}';
    }
}
