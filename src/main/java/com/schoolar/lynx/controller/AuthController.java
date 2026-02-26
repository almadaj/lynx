package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.LoginRequestDTO;
import com.schoolar.lynx.domain.dto.LoginResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterRequestDTO;
import com.schoolar.lynx.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/register")
    public LoginResponseDTO register (@Valid @RequestBody RegisterRequestDTO dto){
        return authService.register(dto);
    }
}
