package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class StudentSummaryDTO {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime birth;
}
