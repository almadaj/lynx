package com.schoolar.lynx.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class QuestionAlternatives {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String alternativeText;

    @NotNull
    @Min(1)
    private int order;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;
}
