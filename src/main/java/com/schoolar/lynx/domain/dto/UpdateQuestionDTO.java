package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionDTO {
    private String header;
    private String body;
    private String footer;
    private Integer privacy;
    private Difficulty difficulty;
    private QuestionType questionType;
    private String expectedAnswer;
    private Language language;
}
