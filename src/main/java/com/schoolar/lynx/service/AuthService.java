package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.*;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserCompanyRepository userCompanyRepository;

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

        String token = jwtService.generateToken(user);
        return new LoginResponseDTO(token);
    }

    public AuthUserDTO me() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails =
                (UserDetailsImpl) authentication.getPrincipal();

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
}
