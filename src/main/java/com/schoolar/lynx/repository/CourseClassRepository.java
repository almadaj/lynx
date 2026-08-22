package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    @Query("""
    SELECT DISTINCT c
    FROM CourseClassStudent cs
    JOIN cs.courseClass c
    LEFT JOIN FETCH c.students s
    LEFT JOIN FETCH s.student
    WHERE cs.student.id = :studentId
    """)
    List<CourseClass> findClassesByStudentId(UUID studentId);

    @Query("""
    SELECT DISTINCT c
    FROM CourseClass c
    LEFT JOIN FETCH c.students s
    LEFT JOIN FETCH s.student
    WHERE c.teacher.id = :teacherId
    """)
    List<CourseClass> findByTeacherIdWithStudents(UUID teacherId);

    @Query("""
    SELECT DISTINCT c
    FROM CourseClass c
    LEFT JOIN FETCH c.students cs
    LEFT JOIN FETCH cs.student
    WHERE c.company.id = :companyId
    """)
    List<CourseClass> findByCompanyIdWithStudents(UUID companyId);

    List<CourseClass> findByTeacherIdAndCompanyId(UUID teacherId, UUID companyId);
}
