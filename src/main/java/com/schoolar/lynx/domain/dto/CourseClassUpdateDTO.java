package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.LanguageLevel;
import com.schoolar.lynx.domain.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CourseClassUpdateDTO {
    private String name;
    private LanguageLevel level;
    private Language language;
    private Integer maxStudents;
    private UUID teacherId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
