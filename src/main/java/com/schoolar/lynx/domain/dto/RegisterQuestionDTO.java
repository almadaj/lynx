package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterQuestionDTO {
    private UUID authorId;
    private UUID companyId;
    private int privacy;
    private QuestionType questionType;
    private Difficulty difficulty;
    private String head;
    private String body;
    private String foot;
    private String expectAnswer;
    private Language language;
}
