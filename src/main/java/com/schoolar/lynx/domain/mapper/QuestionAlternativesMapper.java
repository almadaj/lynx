package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.AlternativesResponseDTO;
import com.schoolar.lynx.domain.model.QuestionAlternatives;

public class QuestionAlternativesMapper {
    public static AlternativesResponseDTO toResponseDTO(
            QuestionAlternatives alternative
    ) {
        AlternativesResponseDTO dto = new AlternativesResponseDTO();

        dto.setId(alternative.getId());
        dto.setAlternativeText(alternative.getAlternativeText());
        dto.setAltOrder(alternative.getAltOrder());
        dto.setCorrect(alternative.isCorrect());

        return dto;
    }
}