package io.prizy.adapters.ranking.persistence.repository;

import java.util.Collection;
import java.util.UUID;

import io.prizy.adapters.ranking.persistence.entity.RankingRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 5/2/2022 9:41 AM
 */


public interface RankingRowJpaRepository extends JpaRepository<RankingRowEntity, UUID> {
  Collection<RankingRowEntity> findAllByContestId(UUID contestId);

  Collection<RankingRowEntity> findAllByContestIdAndUserId(UUID contestId, UUID userId);
}
