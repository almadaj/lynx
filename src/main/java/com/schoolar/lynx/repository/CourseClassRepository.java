package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
}
