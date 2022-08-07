package io.prizy.adapters.reward.persistence.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.reward.persistence.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 15:00
 */

public interface RewardJpaRepository extends JpaRepository<RewardEntity, UUID> {
  Optional<RewardEntity> findTop1ByOrderByCreatedDesc();

  Collection<RewardEntity> findAllByUserId(UUID userId);

  @Query("""
      select reward from Reward reward
      join Pack pack on reward.packId = pack.id
      join Contest contest on pack.contest.id = contest.id
      where contest.id = ?1
    """)
  Collection<RewardEntity> findAllByContestId(UUID contestId);

  @Query("""
      select reward from Reward reward
      join Coupon coupon on reward.packId = coupon.id
      where coupon.expiration <= ?1
    """)
  Collection<RewardEntity> findAllByPackExpiresBefore(Instant date);
}
