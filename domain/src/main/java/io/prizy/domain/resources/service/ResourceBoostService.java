package io.prizy.domain.resources.service;

import java.util.UUID;

import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.referral.ports.ReferralRepository;
import io.prizy.domain.resources.model.ResourceBoost;
import io.prizy.domain.resources.properties.ResourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 15:15
 */


@Transactional
@RequiredArgsConstructor
public class ResourceBoostService {

  private final ReferralRepository referralRepository;
  private final ContestRepository contestRepository;
  private final ResourceProperties properties;

  public ResourceBoost boost(UUID userId, UUID contestId) {
    contestRepository.byId(contestId).orElseThrow(() -> new ContestNotFoundException(contestId));
    var referralsCount = referralRepository.byReferrerId(userId).size();
    return ResourceBoost.builder()
      .userId(userId)
      .contestId(contestId)
      .lives(properties.livesBoostMultiplier() * Math.min(referralsCount, properties.maxLivesBoost()))
      .diamonds((long) properties.diamondsBoostMultiplier() * Math.min(referralsCount, properties.maxDiamondsBoost()))
      .build();
  }

}
