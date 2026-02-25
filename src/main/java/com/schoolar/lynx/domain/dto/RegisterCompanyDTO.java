package com.schoolar.lynx.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;

@Getter
@Setter
public class RegisterCompanyDTO {
    private String publicName;
    private String companyName;

    @NotBlank
    @Email(message = "Email inválido")
    private String email;

    private String phone;
    private String cnpj;
    private String address;
    private boolean hasOnlineClass;
    private boolean isActive;
}
