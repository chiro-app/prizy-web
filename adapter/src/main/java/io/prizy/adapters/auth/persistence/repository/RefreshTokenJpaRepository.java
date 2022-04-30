package io.prizy.adapters.auth.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.auth.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 11:44 PM
 */


public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, UUID> {
  Optional<RefreshTokenEntity> findByUserId(UUID userId);
  Optional<RefreshTokenEntity> findByToken(String token);
}
