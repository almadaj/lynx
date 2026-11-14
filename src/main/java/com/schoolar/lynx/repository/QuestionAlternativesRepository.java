package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.QuestionAlternatives;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionAlternativesRepository extends JpaRepository<QuestionAlternatives, UUID> {
    List<QuestionAlternatives> findAllByQuestion_IdOrderByAltOrderAsc(UUID questionId);
}
