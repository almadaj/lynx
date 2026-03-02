package com.schoolar.lynx.domain.model;

import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.LanguageLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course_class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class CourseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguageLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    private int maxStudents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "courseClass",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CourseClassStudent> students = new ArrayList<>();
}
