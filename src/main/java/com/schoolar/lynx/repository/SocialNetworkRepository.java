package com.schoolar.lynx.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.schoolar.lynx.domain.model.SocialNetwork;
import java.util.Optional;


public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, UUID> {
    Optional<SocialNetwork> findByName(String name);
    boolean existsByName(String name);
}
