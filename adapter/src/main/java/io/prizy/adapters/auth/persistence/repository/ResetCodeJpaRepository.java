package io.prizy.adapters.auth.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.auth.persistence.entity.ResetCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:38 AM
 */


public interface ResetCodeJpaRepository extends JpaRepository<ResetCodeEntity, UUID> {
  Optional<ResetCodeEntity> findByCode(String code);

  void deleteByCode(String code);
}
