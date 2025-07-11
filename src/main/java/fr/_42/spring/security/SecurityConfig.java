package fr._42.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // For now, allow all requests since you only have signin/signup
                // We'll add protection later when you have more pages
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/profile").authenticated()
                        .anyRequest().permitAll()  // Allow all pages for now
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
                // Disable CSRF for now (we'll enable it later)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // Spring Security will automatically find and use any @Service that implements UserDetailsService
    // No need to manually configure it - our CustomUserDetailsService will be auto-discovered

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}