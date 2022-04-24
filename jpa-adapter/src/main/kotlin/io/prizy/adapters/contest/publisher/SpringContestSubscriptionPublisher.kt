package io.prizy.adapters.contest.publisher

import io.prizy.domain.contest.event.ContestSubscriptionCreated
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 4:05 PM
 */

@Component
class SpringContestSubscriptionPublisher : ContestSubscriptionPublisher {

  override suspend fun publish(event: ContestSubscriptionCreated) {
    TODO("Not yet implemented")
  }
}
