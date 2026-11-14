package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AlternativesResponseDTO {
    private UUID id;
    private String alternativeText;
    private int altOrder;
    private boolean isCorrect;
}