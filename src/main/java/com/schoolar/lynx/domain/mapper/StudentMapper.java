package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.StudentSummaryDTO;
import com.schoolar.lynx.domain.model.User;

public class StudentMapper {
    public static StudentSummaryDTO toSummaryDTO(User student) {
        if (student == null) return null;

        StudentSummaryDTO dto = new StudentSummaryDTO();

        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setBirth(student.getBirth());

        return dto;
    }
}
