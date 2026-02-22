package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterCompanyDTO {
    private String publicName;
    private String companyName;
    private String email;
    private String phone;
    private String cnpj;
    private String address;
    private boolean hasOnlineClass;
    private boolean isActive;
}
