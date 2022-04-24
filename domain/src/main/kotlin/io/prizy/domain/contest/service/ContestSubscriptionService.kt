package io.prizy.domain.contest.service

import io.prizy.domain.contest.event.ContestSubscriptionCreated
import io.prizy.domain.contest.exception.ContestNotFoundException
import io.prizy.domain.contest.exception.InsufficientResourcesException
import io.prizy.domain.contest.model.ContestSubscription
import io.prizy.domain.contest.ports.ContestRepository
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher
import io.prizy.domain.contest.ports.ContestSubscriptionRepository
import io.prizy.domain.contest.ports.ReferralRepository
import io.prizy.domain.referral.model.Referral
import io.prizy.domain.resources.service.ResourceService
import java.time.LocalDateTime
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:26 AM
 */

class ContestSubscriptionService(
  private val resourceService: ResourceService,
  private val contestRepository: ContestRepository,
  private val referralRepository: ReferralRepository,
  private val subscriptionPublisher: ContestSubscriptionPublisher,
  private val subscriptionRepository: ContestSubscriptionRepository
) {

  suspend fun isUserSubscribed(contestId: UUID, userId: UUID): Boolean {
    return subscriptionRepository.userSubscribedTo(userId, contestId)
  }

  suspend fun createContestSubscription(contestId: UUID, userId: UUID): ContestSubscription {
    val contest = contestRepository.byId(contestId) ?: throw ContestNotFoundException(contestId)

    if (resourceService.getKeyCount(userId) < contest.cost) {
      throw InsufficientResourcesException()
    }

    val subscription = subscriptionRepository.create(
      ContestSubscription(
        id = UUID.randomUUID(),
        contestId = contestId,
        userId = userId,
        subscriptionDate = LocalDateTime.now()
      )
    )

    resourceService.withdrawKeys(userId, contest.cost)
    subscriptionPublisher.publish(ContestSubscriptionCreated(subscription))

    return subscription
  }

  suspend fun subscribedReferrals(userId: UUID, contestId: UUID): Collection<Referral> {
    val subscribedUserIds = subscriptionRepository
      .subscriptionsOf(contestId)
      .map { subscription -> subscription.userId }
    return referralRepository
      .getUserReferrals(userId)
      .filter(subscribedUserIds::contains)
      .map { referralId -> Referral(referralId) }
  }
}
