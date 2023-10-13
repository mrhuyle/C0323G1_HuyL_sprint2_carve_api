package com.example.carve.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse result = service.register(request);
        if (result.getErrMsg() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authenticationResponse = service.authenticate(request);

        // Set refreshToken as a cookie in the response
        Cookie refreshTokenCookie = new Cookie("refreshToken", authenticationResponse.getRefreshToken());
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // Set the cookie's maximum age in seconds (e.g., 7 days)
        refreshTokenCookie.setPath("/"); // Set the cookie's path
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(refreshTokenCookie);

        authenticationResponse.setRefreshToken("At cookie");

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request,response);
    }
}
