package com.schoolar.lynx.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String name;
    @Email(message="Email inválido")
    private String email;
    private String password;
}
