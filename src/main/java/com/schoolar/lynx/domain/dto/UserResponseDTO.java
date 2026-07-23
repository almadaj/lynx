package com.schoolar.lynx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private LocalDate birth;
    private String profilePhoto;
    private boolean isActive;
    private List<UserCompanyResponse> companies;
    private boolean isAdmin;
}
