package fr._42.spring.services;

import fr._42.spring.models.User;
import fr._42.spring.models.Role;
import fr._42.spring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UsersService {
    @Value("${app.upload.path}")
    private String uploadDirS;

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


    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        try {
            // Validate file size (5MB max)
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("File size must be less than 5MB");
            }

            // Validate content type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            File uploadDir = new File(uploadDirS);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                throw new IllegalArgumentException("Original filename cannot be null or empty");
            }

            // Extract file extension with better error handling
            String extension = "";
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < originalFilename.length() - 1) {
                extension = originalFilename.substring(lastDotIndex);
            } else {
                // Default to .jpg if no extension found
                extension = ".jpg";
            }

            String uniqueFileName = UUID.randomUUID() + extension;
            File dest = new File(uploadDir, uniqueFileName);

            file.transferTo(dest);
            return uniqueFileName;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to store file: " + e.getMessage(), e);
        }
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
   public User updateUserAvatar(Long userId, MultipartFile avatarFile) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String avatarUrl = null;
        if (avatarFile != null && !avatarFile.isEmpty()) {
            // Delete old avatar file if exists
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                deleteAvatarFile(user.getAvatar());
            }

            String storedFilename = store(avatarFile);
            avatarUrl = "/images/" + storedFilename;
        }

        user.setAvatar(avatarUrl);
        return usersRepository.save(user);
    }

    private void deleteAvatarFile(String avatarUrl) {
        try {
            if (avatarUrl != null && avatarUrl.startsWith("/images/")) {
                String filename = avatarUrl.substring("/images/".length());
                File file = new File(uploadDirS, filename);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to delete avatar file: " + e.getMessage());
        }
    }
}
