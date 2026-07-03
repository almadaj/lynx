package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.Question;
import com.schoolar.lynx.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
