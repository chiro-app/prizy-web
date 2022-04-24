package io.prizy.adapters.contest.publisher

import io.prizy.domain.contest.event.ContestCreated
import io.prizy.domain.contest.event.ContestUpdated
import io.prizy.domain.contest.ports.ContestPublisher
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 4:04 PM
 */

@Component
class SpringContestPublisher : ContestPublisher {

  override suspend fun publish(event: ContestCreated) {
    TODO("Not yet implemented")
  }

  override suspend fun publish(event: ContestUpdated) {
    TODO("Not yet implemented")
  }
}
