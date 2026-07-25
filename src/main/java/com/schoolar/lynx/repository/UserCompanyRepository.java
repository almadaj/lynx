package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.model.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCompanyRepository extends JpaRepository<UserCompany, UUID> {
    List<UserCompany> findAllByUserId(UUID userId);
    Optional<UserCompany> findByUserIdAndCompanyId(UUID userId, UUID companyId);
    boolean existsByCompanyIdAndUserId(UUID companyId, UUID userId);
    List<UserCompany> findAllByCompanyIdAndRoleIn(UUID companyId, Collection<Role> roles);
}
