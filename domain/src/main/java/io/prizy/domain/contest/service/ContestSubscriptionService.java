package io.prizy.domain.contest.service;

import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.exception.InsufficientResourcesException;
import io.prizy.domain.contest.event.ContestSubscriptionCreated;
import io.prizy.domain.contest.model.ContestSubscription;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import io.prizy.domain.referral.ports.ReferralRepository;
import io.prizy.domain.referral.model.ReferralNode;
import io.prizy.domain.resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:26 AM
 */

@Transactional
@RequiredArgsConstructor
public class ContestSubscriptionService {

  private final ResourceService resourceService;
  private final ContestRepository contestRepository;
  private final ReferralRepository referralRepository;
  private final ContestSubscriptionPublisher subscriptionPublisher;
  private final ContestSubscriptionRepository subscriptionRepository;

  public Boolean isUserSubscribed(UUID contestId, UUID userId) {
    return subscriptionRepository.userSubscribedTo(userId, contestId);
  }

  public ContestSubscription createContestSubscription(UUID contestId, UUID userId) {
    var contest = contestRepository.byId(contestId).orElseThrow(() -> new ContestNotFoundException(contestId));

    if (resourceService.getKeyCount(userId) < contest.cost()) {
      throw new InsufficientResourcesException();
    }

    var subscription = subscriptionRepository
      .create(new ContestSubscription(UUID.randomUUID(), contestId, userId, Instant.now()));

    subscriptionPublisher.publish(new ContestSubscriptionCreated(subscription));

    return subscription;
  }

  public Collection<ReferralNode> subscribedReferrals(UUID contestId, UUID userId) {
    var subscribedUserIds = subscriptionRepository
      .subscriptionsOf(contestId)
      .stream().map(ContestSubscription::userId)
      .toList();
    return referralRepository
      .byReferrerId(userId)
      .stream().filter(referral -> subscribedUserIds.contains(referral.userId()))
      .toList();
  }
}
