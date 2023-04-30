package com.andreidodu.service;

import com.andreidodu.dto.*;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.Optional;

public interface AuthenticationService {


    AuthenticationResponseDTO register(RegisterRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    AuthenticationResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    UserProfileDTO retrieveProfile(String username) throws ApplicationException;

}
