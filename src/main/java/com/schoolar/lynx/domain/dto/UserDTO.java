package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private LocalDate birth;
    private Boolean isActive;
    private Boolean isAdmin;
}
