package io.prizy.domain.contest.ports

import io.prizy.domain.contest.event.ContestSubscriptionCreated

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:53 AM
 */

interface ContestSubscriptionPublisher {
  suspend fun publish(event: ContestSubscriptionCreated)
}
