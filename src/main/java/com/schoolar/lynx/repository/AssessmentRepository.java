package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.Assessment;
import com.schoolar.lynx.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {
}
