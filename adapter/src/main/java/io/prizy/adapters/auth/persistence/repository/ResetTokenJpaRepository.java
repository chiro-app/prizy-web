package io.prizy.adapters.auth.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.auth.persistence.entity.ResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:38 AM
 */


public interface ResetTokenJpaRepository extends JpaRepository<ResetTokenEntity, UUID> {
  Optional<ResetTokenEntity> findByToken(String token);

  void deleteByToken(String token);
}
