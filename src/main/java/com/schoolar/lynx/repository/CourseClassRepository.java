package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
    @Query("""
    SELECT c
    FROM CourseClass c
    LEFT JOIN FETCH c.students s
    LEFT JOIN FETCH s.student
    WHERE c.id = :id
    """)
    Optional<CourseClass> findByIdWithStudents(UUID id);
}
