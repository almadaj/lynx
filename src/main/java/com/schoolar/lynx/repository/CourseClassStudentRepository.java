package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.CourseClassStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseClassStudentRepository extends JpaRepository<CourseClassStudent, UUID> {
}
