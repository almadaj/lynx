package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassifyQuestionResponseDTO {
    private Difficulty difficulty;
    private double confidence;
    private String reason;
    private String explication;
}