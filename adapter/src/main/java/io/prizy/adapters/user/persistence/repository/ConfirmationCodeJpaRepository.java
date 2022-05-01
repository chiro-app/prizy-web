package io.prizy.adapters.user.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.user.persistence.entity.ConfirmationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:23 PM
 */


public interface ConfirmationCodeJpaRepository extends JpaRepository<ConfirmationCodeEntity, UUID> {
  Optional<ConfirmationCodeEntity> findByCode(String confirmationCode);

  void deleteByCode(String code);
}
