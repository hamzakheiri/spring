package fr._42.spring.services;

import fr._42.spring.models.User;
import fr._42.spring.models.Role;
import fr._42.spring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(String firstName, String lastName, String password, String email, String phoneNumber, Role role) {
        return createUser(firstName, lastName, password, email, phoneNumber, role, null);
    }


    public User authenticateUser(String email, String password) {
        Optional<User> user = usersRepository.findByEmail(email);
        if (user.isPresent() && validatePassword(password, user.get().getPassword())) {
            return user.get();
        }
        return null;
    }

    public User createUser(String firstName, String lastName, String password, String email, String phoneNumber, Role role, String avatarUrl) {
        if (usersRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (usersRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role != null ? role : Role.USER);
        user.setAvatar(avatarUrl); // Set avatar URL (can be null)

        return usersRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

   @Transactional(readOnly = true)
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findByPhoneNumber(phoneNumber);
    }

   public User updateUser(Long id, String firstName, String lastName, String email, String phoneNumber, Role role) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if email is taken by another user
        if (!user.getEmail().equals(email) && usersRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Check if phone number is taken by another user
        if (!user.getPhoneNumber().equals(phoneNumber) && usersRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role);

        return usersRepository.save(user);
    }

   public void updatePassword(Long id, String newPassword) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        usersRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> searchUsersByName(String name) {
        return usersRepository.findByFullNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<User> searchUsersByFirstName(String firstName) {
        return usersRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    @Transactional(readOnly = true)
    public List<User> searchUsersByLastName(String lastName) {
        return usersRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRole(Role role) {
        return usersRepository.findByRole(role);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        return !usersRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isPhoneNumberAvailable(String phoneNumber) {
        return !usersRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    public long getUserCountByRole(Role role) {
        return usersRepository.countByRole(role);
    }
}
