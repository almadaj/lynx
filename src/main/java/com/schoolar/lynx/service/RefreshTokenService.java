package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.model.RefreshToken;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.refresh-token.expiration}")
    private long expirationSeconds;

    private final SecureRandom secureRandom = new SecureRandom();

    public String create(User user) {
        String token = generateToken();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(hash(token))
                .createdAt(Instant.now())
                .expiresAt(
                        Instant.now()
                                .plus(expirationSeconds, ChronoUnit.SECONDS)
                )
                .build();

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public RefreshToken validate(String token) {
        String tokenHash = hash(token);

        RefreshToken refreshToken =
                refreshTokenRepository.findByTokenHash(tokenHash)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Refresh token inválido"
                        ));

        if (!refreshToken.isValid()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Refresh token expirado ou revogado"
            );
        }
        return refreshToken;
    }

    public void revoke(String token) {
        String tokenHash = hash(token);
        refreshTokenRepository.findByTokenHash(tokenHash)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevokedAt(Instant.now());
                    refreshTokenRepository.save(refreshToken);
                });
    }

    private String generateToken() {
        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String hash(String token) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8)
            );

            return Base64.getEncoder()
                    .encodeToString(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 não disponível",
                    e
            );
        }
    }
}