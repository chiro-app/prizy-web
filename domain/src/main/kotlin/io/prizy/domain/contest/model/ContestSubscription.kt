package io.prizy.domain.contest.model

import java.time.LocalDateTime
import java.util.UUID

data class ContestSubscription(
  val id: UUID,
  val userId: UUID,
  val contestId: UUID,
  val subscriptionDate: LocalDateTime
)
