package com.schoolar.lynx.domain.model;

import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @NotNull
    @Min(0)
    @Max(2)
    private int privacy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String header;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String footer;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String expectedAnswer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
