// src/main/java/fr/_42/spring/exception/GlobalExceptionHandler.java
package fr._42.spring.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDenied(HttpServletResponse response) throws IOException {
        response.sendRedirect("/signin");
    }
}