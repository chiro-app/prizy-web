package io.prizy.adapters.contest.persistence

import io.prizy.domain.contest.model.ContestSubscription
import io.prizy.domain.contest.ports.ContestSubscriptionRepository
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 4:06 PM
 */

@Component
class ContestSubscriptionRepositoryImpl : ContestSubscriptionRepository {

  override suspend fun subscriptionsOf(contestId: UUID): Collection<ContestSubscription> {
    TODO("Not yet implemented")
  }

  override suspend fun userSubscribedTo(userId: UUID, contestId: UUID): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun create(subscription: ContestSubscription): ContestSubscription {
    TODO("Not yet implemented")
  }
}
