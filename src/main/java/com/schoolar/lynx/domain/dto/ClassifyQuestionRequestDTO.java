package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClassifyQuestionRequestDTO {
    private String statement;
    private String expectedAnswer;
    private List<String> alternatives;
}
