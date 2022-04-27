package io.prizy.domain.contest.ports;

import io.prizy.domain.contest.model.ContestSubscription;

import java.util.Collection;
import java.util.UUID;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:27 AM
 */

public interface ContestSubscriptionRepository {
  Collection<ContestSubscription> subscriptionsOf(UUID contestId);
  Boolean userSubscribedTo(UUID userId, UUID contestId);
  ContestSubscription create(ContestSubscription subscription);
}
