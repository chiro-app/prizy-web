package io.prizy.adapters.contest.persistence.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.contest.persistence.entity.ContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:13 PM
 */

public interface ContestJpaRepository extends JpaRepository<ContestEntity, UUID> {
  Collection<ContestEntity> findAllByToDateBetween(Instant from, Instant to);

  Collection<ContestEntity> findAllByToDateBefore(Instant to);

  Optional<ContestEntity> findByAccessCode(String accessCode);

  @Query("""
      select c from Contest c
      inner join Pack p on p.contest = c
      where p.id = ?1
    """)
  Optional<ContestEntity> findByPackId(UUID packId);
}
