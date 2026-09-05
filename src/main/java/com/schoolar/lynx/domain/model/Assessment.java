package com.schoolar.lynx.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assessment", schema = "academico")
@Builder
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String title;

    @DecimalMin("0.0")
    @DecimalMax("1000.0")
    @Column
    private Double maxScore;

    @Column
    private LocalDate date;

    @Column
    private LocalDate limitDate;

    @ManyToOne
    @JoinColumn(name = "course_class_id", nullable = false)
    CourseClass courseClass;
}
