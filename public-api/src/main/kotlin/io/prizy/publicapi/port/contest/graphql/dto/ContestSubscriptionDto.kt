package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.Instant
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:37 PM
 */

@GraphQLName("ContestSubscription")
data class ContestSubscriptionDto(
  val id: UUID,
  val userId: UUID,
  val contestId: UUID,
  val dateTime: Instant
)
