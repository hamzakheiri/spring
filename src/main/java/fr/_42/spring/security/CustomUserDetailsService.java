package fr._42.spring.security;

import fr._42.spring.models.User;
import fr._42.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of UserDetailsService that integrates with our UserService.
 * This service is called by Spring Security during authentication to load user details.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads user details by username (email in our case).
     * This method is called by Spring Security's authentication manager.
     * 
     * @param username The username (email) to search for
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // In our system, username is actually the email
        User user = userService.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        
        // Wrap our User entity in CustomUserDetails for Spring Security
        return new CustomUserDetails(user);
    }
}
