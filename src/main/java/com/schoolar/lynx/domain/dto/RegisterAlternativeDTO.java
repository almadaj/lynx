package com.schoolar.lynx.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAlternativeDTO {
    private int altOrder;
    private String alternativeText;

    @JsonProperty("isCorrect")
    private boolean correct;
}
