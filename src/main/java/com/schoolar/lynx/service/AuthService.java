package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.LoginRequestDTO;
import com.schoolar.lynx.domain.dto.LoginResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterRequestDTO;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponseDTO(token);
    }

    public LoginResponseDTO register(RegisterRequestDTO dto) {
        //TODO: Validação de email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isAdmin(false)
                .isActive(true)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new LoginResponseDTO(token);
    }
}
