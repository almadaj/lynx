package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.*;
import com.schoolar.lynx.domain.model.RefreshToken;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserCompanyRepository userCompanyRepository;

    public void login(LoginRequestDTO dto, HttpServletResponse response) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.create(user);

        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);
    }

    public void refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
            String refreshToken = extractRefreshToken(request);
            RefreshToken token = refreshTokenService.validate(refreshToken);
            String accessToken = jwtService.generateToken(token.getUser());
            addAccessTokenCookie(response, accessToken);
    }

    public String register(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .birth(dto.getBirth())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isActive(true)
                .build();

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public AuthUserDTO me() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado"
            );
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        List<UserCompanyResponse> companies =
                userCompanyRepository.findAllByUserId(user.getId())
                        .stream()
                        .map(uc -> UserCompanyResponse.builder()
                                .userCompanyId(uc.getId())
                                .companyId(uc.getCompany().getId())
                                .companyName(uc.getCompany().getCompanyName())
                                .publicName(uc.getCompany().getPublicName())
                                .role(uc.getRole())
                                .build())
                        .toList();

        return AuthUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .companies(companies)
                .build();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
            String refreshToken = extractRefreshToken(request);
            refreshTokenService.revoke(refreshToken);
            deleteAccessTokenCookie(response);
            deleteRefreshTokenCookie(response);
    }

    private void deleteAccessTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie
                .from("access_token", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
    }
    private void deleteRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/auth/refresh")
                .maxAge(Duration.ZERO)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
    }
    private void addAccessTokenCookie(HttpServletResponse response, String token){
        ResponseCookie cookie = ResponseCookie
                .from("access_token", token)
                .httpOnly(true)
                .secure(false) //TODO: em prod isso deve ser true
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
    }
    private void addRefreshTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", token)
                .httpOnly(true)
                .secure(false) //TODO: em prod isso deve ser true
                .sameSite("Lax")
                .path("/auth/refresh")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
    }
    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Refresh token não encontrado"
        );
    }
}
