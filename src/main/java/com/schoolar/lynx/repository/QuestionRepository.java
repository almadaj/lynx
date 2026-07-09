package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query("""
    SELECT q
    FROM Question q
    WHERE q.deletedAt IS NULL
        AND (
            LOWER(q.header) LIKE LOWER(CONCAT('%', :search, '%'))
            OR
            LOWER(q.body) LIKE LOWER(CONCAT('%', :search, '%'))
            OR
            LOWER(q.footer) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Question> findAllActive(@Param("search")String search, Pageable pageable);
}
