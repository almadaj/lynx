package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Role;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserCompanyResponse {
    private UUID userCompanyId;
    private UUID companyId;
    private String companyName;
    private String publicName;
    private boolean active;
    private Role role;
}
