package com.schoolar.lynx.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UpdateCompanyDTO {
    private String publicName;
    private String companyName;
    @Email(message = "Email inválido")
    private String email;
    private String phone;
    private String cnpj;
    private String address;
    private boolean hasOnlineClass;
    private boolean isActive;
}
