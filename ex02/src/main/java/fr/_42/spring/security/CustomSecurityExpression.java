package fr._42.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("customSecurity")
public class CustomSecurityExpression {
    public boolean isConfirmed(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails user) {
            return user.isConfirmed();
        }
        return false;
    }
}