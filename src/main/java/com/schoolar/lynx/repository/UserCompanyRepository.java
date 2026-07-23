package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserCompanyRepository extends JpaRepository<UserCompany, UUID> {
    List<UserCompany> findAllByUserId(UUID userId);
}
