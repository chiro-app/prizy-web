package io.prizy.domain.contest.usecase;

import java.time.Instant;
import java.util.UUID;

import io.prizy.domain.contest.event.ContestSubscriptionCreated;
import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.exception.InsufficientResourcesException;
import io.prizy.domain.contest.model.ContestSubscription;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:34
 */


@Component
@RequiredArgsConstructor
public class SubscribeToContestUseCase {

  private final ContestRepository contestRepository;
  private final ResourceRepository resourceRepository;
  private final ContestSubscriptionRepository subscriptionRepository;
  private final ContestSubscriptionPublisher subscriptionPublisher;

  @Transactional
  public ContestSubscription subscribe(UUID contestId, UUID userId) {
    var contest = contestRepository.byId(contestId).orElseThrow(() -> new ContestNotFoundException(contestId));

    if (resourceRepository.countKeys(userId) < contest.cost()) {
      throw new InsufficientResourcesException();
    }

    var subscription = subscriptionRepository
      .create(ContestSubscription.builder()
        .userId(userId)
        .contestId(contestId)
        .dateTime(Instant.now())
        .build()
      );

    subscriptionPublisher.publish(new ContestSubscriptionCreated(subscription));

    return subscription;
  }

}
