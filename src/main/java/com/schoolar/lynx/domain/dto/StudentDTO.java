package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class StudentDTO {
    private UUID id;
    private String name;
    private String email;
    private String profilePhoto;
    private LocalDate birth;
}
