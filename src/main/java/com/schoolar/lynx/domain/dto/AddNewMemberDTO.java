package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddNewMemberDTO {
    private String email;
    private Role role;
}
