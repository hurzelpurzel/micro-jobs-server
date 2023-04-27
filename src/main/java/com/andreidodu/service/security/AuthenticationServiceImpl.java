package com.andreidodu.service.security;



import com.andreidodu.dto.AuthenticationRequestDTO;
import com.andreidodu.dto.AuthenticationResponseDTO;
import com.andreidodu.dto.RegisterRequestDTO;
import com.andreidodu.model.Role;
import com.andreidodu.model.User;
import com.andreidodu.model.Token;
import com.andreidodu.model.TokenType;
import com.andreidodu.repository.TokenRepository;
import com.andreidodu.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        System.out.println("encoded password: " + encodedPassword);
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .status(0)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtServiceImpl.generateToken(user);
        var refreshToken = jwtServiceImpl.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtServiceImpl.generateToken(user);
        var refreshToken = jwtServiceImpl.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
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

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponseDTO refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return new AuthenticationResponseDTO();
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtServiceImpl.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByUsername(userEmail)
                    .orElseThrow();
            if (jwtServiceImpl.isTokenValid(refreshToken, user)) {
                var accessToken = jwtServiceImpl.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                return authResponse;
            }
        }
        return new AuthenticationResponseDTO();
    }
}
