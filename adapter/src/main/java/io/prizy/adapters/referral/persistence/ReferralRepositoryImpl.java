package io.prizy.adapters.referral.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.referral.mapper.ReferralNodeMapper;
import io.prizy.adapters.referral.persistence.repository.ReferralNodeJpaRepository;
import io.prizy.domain.referral.model.ReferralNode;
import io.prizy.domain.referral.ports.ReferralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 4:07 PM
 */

@Component
@RequiredArgsConstructor
public class ReferralRepositoryImpl implements ReferralRepository {

  private final ReferralNodeJpaRepository jpaRepository;

  @Override
  public Collection<ReferralNode> byReferrerId(UUID userId) {
    return jpaRepository
      .findAllByReferrerId(userId)
      .stream()
      .map(ReferralNodeMapper::map)
      .toList();
  }

  @Override
  public Optional<ReferralNode> byReferralCode(String referralCode) {
    return jpaRepository
      .findByCode(referralCode)
      .map(ReferralNodeMapper::map);
  }

  @Override
  public Optional<ReferralNode> referrer(UUID id) {
    return jpaRepository
      .findById(id)
      .flatMap(node -> Optional.ofNullable(node.getReferrerId()))
      .flatMap(jpaRepository::findById)
      .map(ReferralNodeMapper::map);
  }

  @Override
  public Optional<ReferralNode> byUserId(UUID userId) {
    return jpaRepository.findById(userId).map(ReferralNodeMapper::map);
  }

  @Override
  public ReferralNode save(ReferralNode node) {
    return ReferralNodeMapper.map(jpaRepository.save(ReferralNodeMapper.map(node)));
  }
}
