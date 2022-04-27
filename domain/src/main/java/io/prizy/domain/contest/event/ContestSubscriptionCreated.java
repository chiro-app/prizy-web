package io.prizy.domain.contest.event;

import io.prizy.domain.contest.model.ContestSubscription;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:47 AM
 */

public record ContestSubscriptionCreated(
  ContestSubscription subscription
) {
}
