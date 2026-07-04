package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QuestionResponseDTO {
    private UUID id;
    private UUID authorId;
    private UUID companyId;
    private int privacy;
    private Difficulty difficulty;
    private QuestionType questionType;
    private String header;
    private String body;
    private String footer;
    private String expectedAnswer;
    private Language language;
}
