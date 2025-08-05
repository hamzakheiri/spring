package fr._42.spring.models;

import fr._42.spring.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{user.firstName.required}")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "{user.lastName.required}")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "{user.password.required}")
    @ValidPassword(message = "{user.password.pattern}")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @NotBlank(message = "{user.email.required}")
    @Email(message = "{user.email.pattern}")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "{user.phoneNumber.required}")
    @Pattern(regexp = "\\+\\d+\\(\\d+\\)\\d+", message = "{user.phoneNumber.pattern}")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "confirmation", columnDefinition = "boolean default true")
    private boolean confirmation;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public User(Long id, String firstName, String lastName, String password, String email, String phoneNumber, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return confirmation == user.confirmation && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(avatar, user.avatar) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, password, avatar, email, phoneNumber, confirmation, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", confirmation=" + confirmation +
                ", role=" + role +
                '}';
    }
}
