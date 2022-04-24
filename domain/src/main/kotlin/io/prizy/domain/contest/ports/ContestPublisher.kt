package io.prizy.domain.contest.ports

import io.prizy.domain.contest.event.ContestCreated
import io.prizy.domain.contest.event.ContestUpdated

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:50 AM
 */

interface ContestPublisher {
  suspend fun publish(event: ContestCreated)
  suspend fun publish(event: ContestUpdated)
}
