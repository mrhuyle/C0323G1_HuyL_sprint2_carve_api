package com.example.carve.config;

import com.example.carve.token.repository.TokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        System.out.println("__________________Logout service");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Set the cookie's max age to 0 to remove it
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("__________NO _____HEADER_______");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
