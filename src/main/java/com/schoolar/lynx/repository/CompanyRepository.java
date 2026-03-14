package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);
    @Query("""
    SELECT c
    FROM Company c
    WHERE c.principalTeacher.id = :userId
    LIMIT 1
    """)
    Optional<Company> findByPrincipalTeacherId(UUID userId);

}
