package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateQuestionDTO {
    private String header;
    @NotBlank
    private String body;

    private String footer;

    @NotNull
    @Min(0)
    @Min(2)
    private Integer privacy;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private QuestionType questionType;

    @NotNull
    private Language language;
    private String expectedAnswer;

    private List<RegisterAlternativeDTO> alternatives;
}
