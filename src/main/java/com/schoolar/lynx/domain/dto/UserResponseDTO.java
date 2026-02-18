package com.schoolar.lynx.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private LocalDate birth;
    private Boolean isActive;
    private Boolean isAdmin;
}
