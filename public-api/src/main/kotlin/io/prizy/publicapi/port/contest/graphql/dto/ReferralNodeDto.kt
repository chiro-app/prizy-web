package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:10 PM
 */

@GraphQLName("ReferralNode")
data class ReferralNodeDto(
  val userId: UUID,
  val confirmed: Boolean,
)
