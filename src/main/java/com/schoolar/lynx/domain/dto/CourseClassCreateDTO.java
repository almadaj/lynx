package com.schoolar.lynx.domain.dto;

import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.LanguageLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CourseClassCreateDTO {
    private String name;
    private LanguageLevel level;
    private Language language;
    private int maxStudents;
    private UUID teacherId;
    private UUID companyId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
