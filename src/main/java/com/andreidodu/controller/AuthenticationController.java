package com.andreidodu.controller;

import com.andreidodu.dto.*;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.security.AuthenticationServiceImpl;
import com.andreidodu.service.security.JwtServiceImpl;
import com.andreidodu.service.security.LogoutServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    private final LogoutServiceImpl logoutServiceImpl;

    private final LogoutServiceImpl logoutService;

    final private JwtServiceImpl jwtServiceImpl;

    @PostMapping("/register")

    public ResponseEntity<AuthenticationResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> retrieveProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(this.service.retrieveProfile(this.jwtServiceImpl.extractUsernameFromAuthorizzation(authorization)));
    }

    @PostMapping("/refreshToken")
    public AuthenticationResponseDTO refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return service.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        logoutService.logout(request, response, authentication);
    }

}
