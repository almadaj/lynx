package com.schoolar.lynx.repository;

import com.schoolar.lynx.domain.model.CompanySocialNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CompanySocialNetworkRepository
        extends JpaRepository<CompanySocialNetwork, UUID> {

    List<CompanySocialNetwork> findByCompanyId(UUID companyId);

    boolean existsByCompanyIdAndSocialNetworkId(UUID companyId, UUID socialNetworkId);

    @Modifying
    @Query("DELETE FROM CompanySocialNetwork csn WHERE csn.company.id = :companyId")
    void deleteByCompanyId(@Param("companyId") UUID companyId);
}
