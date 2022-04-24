package io.prizy.domain.contest.ports

import io.prizy.domain.contest.model.ContestSubscription
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:27 AM
 */

interface ContestSubscriptionRepository {
  suspend fun subscriptionsOf(contestId: UUID): Collection<ContestSubscription>
  suspend fun userSubscribedTo(userId: UUID, contestId: UUID): Boolean
  suspend fun create(subscription: ContestSubscription): ContestSubscription
}
