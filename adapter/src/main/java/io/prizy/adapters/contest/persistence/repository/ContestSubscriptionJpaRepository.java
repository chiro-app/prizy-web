package io.prizy.adapters.contest.persistence.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.contest.persistence.entity.ContestSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestSubscriptionJpaRepository extends JpaRepository<ContestSubscriptionEntity, UUID> {
  Collection<ContestSubscriptionEntity> findAllByContestId(UUID contestId);

  Collection<ContestSubscriptionEntity> findAllByUserId(UUID userId);

  Boolean existsByContestIdAndUserId(UUID contestId, UUID userId);

  Optional<ContestSubscriptionEntity> findByContestIdAndUserId(UUID contestId, UUID userId);
}
