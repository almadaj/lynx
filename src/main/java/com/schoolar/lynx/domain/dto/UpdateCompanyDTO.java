package com.schoolar.lynx.domain.dto;

import java.util.UUID;

public class UpdateCompanyDTO {
    private String publicName;
    private String companyName;
    private String email;
    private String phone;
    private String cnpj;
    private String address;
    private boolean hasOnlineClass;
    private boolean isActive;
    private UUID principalTeacherId;
}
