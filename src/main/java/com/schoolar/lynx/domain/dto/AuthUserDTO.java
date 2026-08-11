package com.schoolar.lynx.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AuthUserDTO {
    private UUID id;
    private String name;
    private String email;
    private List<UserCompanyResponse> companies;
}