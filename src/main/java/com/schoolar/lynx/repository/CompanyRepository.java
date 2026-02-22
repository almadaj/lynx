package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findById(UUID id);
}
