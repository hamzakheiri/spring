package fr._42.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Lazy
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/signin", "/signup", "/login", "/images/**", "/css/**", "/js/**").permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .formLogin(form -> form
                        .loginPage("/signin")           // Your custom login page
                        .loginProcessingUrl("/login")   // Where Spring Security processes login
                        .usernameParameter("email")     // Your form uses 'email' not 'username'
                        .passwordParameter("password")  // Your form uses 'password' (default)
                        .defaultSuccessUrl("/profile", true)
                        .failureUrl("/signin?error=true")     // Redirect after failed login
                        .permitAll()                    // Allow everyone to access login page
                )

                .rememberMe(remember -> remember
                        .rememberMeParameter("rememberMe")
                        .tokenValiditySeconds(86400) // 1 day
                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(customUserDetailsService)
                        .key("uniqueAndSecretKey") // Use a strong, unique key
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                // Enable CSRF protection
                .csrf(csrf -> csrf
                       .ignoringRequestMatchers("/api/**") // Example: exclude REST API endpoints if you have any
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}