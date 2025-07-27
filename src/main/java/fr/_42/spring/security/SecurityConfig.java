package fr._42.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configure which pages require authentication
                .authorizeHttpRequests(authz -> authz
                        // Public pages - accessible without authentication
                        .requestMatchers("/signin", "/signup", "/login", "/images/**", "/css/**", "/js/**").permitAll()

                        // Admin pages - require ADMIN role
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // All other pages - require authentication (any authenticated user)
                        .anyRequest().authenticated()
                )
                // Configure session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                // Configure form-based login for when you're ready
                .formLogin(form -> form
                        .loginPage("/signin")           // Your custom login page
                        .loginProcessingUrl("/login")   // Where Spring Security processes login
                        .usernameParameter("email")     // Your form uses 'email' not 'username'
                        .passwordParameter("password")  // Your form uses 'password' (default)
                        .defaultSuccessUrl("/profile", true)
                        .failureUrl("/signin?error=true")     // Redirect after failed login
                        .permitAll()                    // Allow everyone to access login page
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                // Enable CSRF protection
                .csrf(csrf -> csrf
                        // CSRF is enabled by default for state-changing operations (POST, PUT, DELETE)
                        // We can customize which endpoints to exclude if needed
                        .ignoringRequestMatchers("/api/**") // Example: exclude REST API endpoints if you have any
                );

        return http.build();
    }

    // Spring Security will automatically find and use any @Service that implements UserDetailsService
    // No need to manually configure it - our CustomUserDetailsService will be auto-discovered

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}