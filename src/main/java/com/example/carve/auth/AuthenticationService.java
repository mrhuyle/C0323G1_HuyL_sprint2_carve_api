package com.example.carve.auth;

import com.example.carve.config.JwtService;
import com.example.carve.token.model.Token;
import com.example.carve.token.model.TokenType;
import com.example.carve.token.repository.TokenRepository;
import com.example.carve.user.model.User;
import com.example.carve.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userService.getUser(request.getUsername()) != null) {
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .errMsg("Existed username")
                    .build();
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .img("/src/assets/img/user_img.webp")
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(userService.findRoleByName(request.getRoles().get(0))))
                .build();
        var savedUser = userService.saveUser(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .errMsg(null)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userService.getUser(request.getUsername());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens (User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        System.out.println("__________Valid user tokens: ____"+validUserTokens);
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        final String username;
        if (cookies == null) {
            return;
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            return;
        }
        username = jwtService.extractUsername(refreshToken); //extract username
        if (username != null) {
            var user = this.userService.getUser(username);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                System.out.println("_______________Revoke___________");
                saveUserToken(user, accessToken);
                Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
                refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // Set the cookie's maximum age in seconds (e.g., 7 days)
                refreshTokenCookie.setPath("/"); // Set the cookie's path
                refreshTokenCookie.setHttpOnly(true);
                response.addCookie(refreshTokenCookie);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken("At cookie")
                        .errMsg("Refresh successfully")
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
