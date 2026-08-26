package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.AuthUserDTO;
import com.schoolar.lynx.domain.dto.LoginRequestDTO;
import com.schoolar.lynx.domain.dto.RegisterRequestDTO;
import com.schoolar.lynx.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequestDTO dto,
            HttpServletResponse response
    ) {
        String token = authService.login(dto);
        addAuthCookie(response, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        clearAuthCookie(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register (
            @Valid @RequestBody RegisterRequestDTO dto,
            HttpServletResponse response
            ){
        String token = authService.register(dto);
        addAuthCookie(response, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AuthUserDTO> me() {
        return ResponseEntity.ok(authService.me());
    }

    private void addAuthCookie(
            HttpServletResponse response,
            String token
    ){
        ResponseCookie cookie = ResponseCookie
                .from("access_token", token)
                .httpOnly(true)
                .secure(false) //TODO: em prod isso deve ser true
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
    }

    private void clearAuthCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie
                .from("access_token", "")
                .httpOnly(true)
                .secure(false) //TODO: em prod isso deve ser true
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
    }
}
