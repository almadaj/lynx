package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.model.CourseClass;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AssessmentRequestDTO {
    @NotBlank
    @Size(max = 50)
    private String title;

    @DecimalMin("0.0")
    @DecimalMax("1000.0")
    private Double maxScore;

    @NotNull
    private LocalDate date;

    private LocalDate limitDate;

    @NotNull
    private UUID courseClassId;
}
