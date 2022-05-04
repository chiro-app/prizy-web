package io.prizy.adapters.reward.persistence.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.reward.persistence.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 15:00
 */

public interface RewardJpaRepository extends JpaRepository<RewardEntity, UUID> {
  Optional<RewardEntity> findTop1ByOrderByCreatedDesc();

  Collection<RewardEntity> findAllByUserId(UUID userId);
}
