package io.prizy.adapters.referral.persistence.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.referral.persistence.entity.ReferralNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralNodeJpaRepository extends JpaRepository<ReferralNodeEntity, UUID> {
  Collection<ReferralNodeEntity> findAllByReferrerId(UUID referrerId);

  Optional<ReferralNodeEntity> findByCode(String code);
}
