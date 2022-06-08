package io.prizy.domain.resources.service;

import java.util.UUID;

import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.service.ContestSubscriptionService;
import io.prizy.domain.resources.model.ResourceBoost;
import io.prizy.domain.resources.properties.ResourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 15:15
 */


@Service
@Transactional
@RequiredArgsConstructor
public class ResourceBoostService {

  private final ContestRepository contestRepository;
  private final ContestSubscriptionService subscriptionService;
  private final ResourceProperties resourceProperties;

  public ResourceBoost boost(UUID userId, UUID contestId) {
    if (!contestRepository.exists(contestId)) {
      throw new ContestNotFoundException(contestId);
    }
    var referralsCount = subscriptionService.subscribedReferralsCount(contestId, userId);
    return ResourceBoost.builder()
      .userId(userId)
      .contestId(contestId)
      .lives(resourceProperties.livesBoostMultiplier() * Math.min(referralsCount,
        resourceProperties.maxBoostReferrals()))
      .diamonds((long) resourceProperties.diamondsBoostMultiplier() * Math.min(referralsCount,
        resourceProperties.maxBoostReferrals()))
      .build();
  }

}
