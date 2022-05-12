package io.prizy.domain.contest.ports;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.model.ContestSubscription;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:27 AM
 */

public interface ContestSubscriptionRepository {
  Collection<ContestSubscription> ofContest(UUID contestId);

  Collection<ContestSubscription> ofUser(UUID userId);

  Optional<ContestSubscription> subscriptionOfUser(UUID userId, UUID contestId);

  Boolean userSubscribedTo(UUID userId, UUID contestId);

  ContestSubscription create(ContestSubscription subscription);
}
