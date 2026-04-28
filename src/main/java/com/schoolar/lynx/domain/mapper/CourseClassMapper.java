package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.CourseClassResponseDTO;
import com.schoolar.lynx.domain.dto.StudentSummaryDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.model.CourseClass;
import com.schoolar.lynx.service.UserService;

import java.util.List;
import java.util.UUID;

public class CourseClassMapper {
    public static CourseClassResponseDTO toDTO(CourseClass courseClass) {
        CourseClassResponseDTO dto = new CourseClassResponseDTO();

        dto.setId(courseClass.getId());
        dto.setName(courseClass.getName());
        dto.setLevel(courseClass.getLevel());
        dto.setLanguage(courseClass.getLanguage());
        dto.setMaxStudents(courseClass.getMaxStudents());
        dto.setCompanyId(courseClass.getCompany().getId());
        dto.setStartDate(courseClass.getStartDate());
        dto.setEndDate(courseClass.getEndDate());
        dto.setCreatedAt(courseClass.getCreatedAt());
        dto.setUpdatedAt(courseClass.getUpdatedAt());
        dto.setTeacher(UserMapper.toResponseDTO(courseClass.getTeacher()));

        List<StudentSummaryDTO> students =
                courseClass.getStudents()
                        .stream()
                        .map(cs -> StudentMapper.toSummaryDTO(cs.getStudent()))
                        .toList();

        dto.setStudents(students);
        return dto;
    }
}
