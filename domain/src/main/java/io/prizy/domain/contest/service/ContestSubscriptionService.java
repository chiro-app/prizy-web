package io.prizy.domain.contest.service;

import java.util.Collection;
import java.util.UUID;

import io.prizy.domain.contest.model.ContestSubscription;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import io.prizy.domain.referral.model.ReferralNode;
import io.prizy.domain.referral.ports.ReferralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:26 AM
 */


@Service
@RequiredArgsConstructor
public class ContestSubscriptionService {

  private final ReferralRepository referralRepository;
  private final ContestSubscriptionRepository subscriptionRepository;

  public Collection<ContestSubscription> ofUser(UUID user) {
    return subscriptionRepository.ofUser(user);
  }

  public Collection<ReferralNode> subscribedReferrals(UUID contestId, UUID userId) {
    var subscribedUserIds = subscriptionRepository
      .ofContest(contestId)
      .stream()
      .map(ContestSubscription::userId)
      .toList();
    return referralRepository
      .byReferrerId(userId)
      .stream()
      .filter(referral -> subscribedUserIds.contains(referral.userId()))
      .toList();
  }

  public Integer subscribedReferralsCount(UUID contestId, UUID userId) {
    return subscribedReferrals(contestId, userId).size();
  }
}
