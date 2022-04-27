package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.model.ContestSubscription
import io.prizy.publicapi.port.contest.graphql.dto.ContestSubscriptionDto

object ContestSubscriptionDtoMapper {

  fun map(subscription: ContestSubscription): ContestSubscriptionDto = ContestSubscriptionDto(
    id = subscription.id,
    userId = subscription.userId,
    contestId = subscription.contestId,
    dateTime = subscription.dateTime,
  )
}