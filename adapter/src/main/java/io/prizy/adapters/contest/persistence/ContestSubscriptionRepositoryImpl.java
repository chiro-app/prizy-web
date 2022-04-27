package io.prizy.adapters.contest.persistence;

import io.prizy.adapters.contest.mapper.ContestSubscriptionMapper;
import io.prizy.adapters.contest.persistence.repository.ContestSubscriptionJpaRepository;
import io.prizy.domain.contest.model.ContestSubscription;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 4:06 PM
 */

@Component
@RequiredArgsConstructor
public class ContestSubscriptionRepositoryImpl implements ContestSubscriptionRepository {

  private final ContestSubscriptionJpaRepository jpaRepository;

  @Override
  public Collection<ContestSubscription> subscriptionsOf(UUID contestId) {
    return jpaRepository
      .findAllByContestId(contestId)
      .stream().map(ContestSubscriptionMapper::map)
      .toList();
  }

  @Override
  public Boolean userSubscribedTo(UUID userId, UUID contestId) {
    return jpaRepository.existsByContestIdAndUserId(contestId, userId);
  }

  @Override
  public ContestSubscription create(ContestSubscription subscription) {
    var entity = jpaRepository.save(ContestSubscriptionMapper.map(subscription));
    return ContestSubscriptionMapper.map(entity);
  }
}
