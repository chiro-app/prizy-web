package io.prizy.publicapi.port.contest.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.contest.service.ContestSubscriptionService
import io.prizy.publicapi.port.contest.graphql.dto.ContestDto
import io.prizy.publicapi.port.contest.graphql.dto.ReferralNodeDto
import io.prizy.publicapi.port.contest.mapper.ContestDtoMapper
import io.prizy.publicapi.port.contest.mapper.ReferralDtoMapper
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 2:18 PM
 */

@Component
class ContestQuery(
  private val contestService: ContestService,
  private val contestSubscriptionService: ContestSubscriptionService
) : Query {

  suspend fun contestById(id: UUID): ContestDto? {
    return contestService
      .contestById(id)
      .orElse(null)
      ?.let(ContestDtoMapper::map)
  }

  suspend fun contests(): List<ContestDto> {
    return contestService
      .contests()
      .map(ContestDtoMapper::map)
  }

  suspend fun listAllContests(): List<ContestDto> {
    return contestService
      .listAllContests()
      .map(ContestDtoMapper::map)
  }

  suspend fun isUserSubscribed(contestId: UUID, userId: UUID): Boolean {
    return contestSubscriptionService.isUserSubscribed(contestId, userId)
  }

  suspend fun subscribedReferrals(contestId: UUID): List<ReferralNodeDto> {
    val userId = UUID.randomUUID()
    return contestSubscriptionService
      .subscribedReferrals(userId, contestId)
      .map(ReferralDtoMapper::map)
  }
}
