package io.prizy.publicapi.port.contest.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.contest.service.ContestSubscriptionService
import io.prizy.publicapi.port.contest.graphql.dto.ContestDto
import io.prizy.publicapi.port.contest.graphql.dto.ContestSubscriptionDto
import io.prizy.publicapi.port.contest.graphql.dto.CreateContestDto
import io.prizy.publicapi.port.contest.graphql.dto.UpdateContestDto
import io.prizy.publicapi.port.contest.mapper.ContestDtoMapper
import io.prizy.publicapi.port.contest.mapper.ContestSubscriptionDtoMapper
import io.prizy.publicapi.port.contest.mapper.CreateContestMapper
import io.prizy.publicapi.port.contest.mapper.UpdateContestMapper
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:26 PM
 */

@Component
class ContestMutation(
  private val contestService: ContestService,
  private val contestSubscriptionService: ContestSubscriptionService
) : Mutation {

  suspend fun createContest(request: CreateContestDto): ContestDto = contestService
    .createContest(CreateContestMapper.map(request))
    .let(ContestDtoMapper::map)

  suspend fun updateContest(request: UpdateContestDto): ContestDto = contestService
    .updateContest(UpdateContestMapper.map(request))
    .let(ContestDtoMapper::map)

  suspend fun createContestSubscription(contestId: UUID): ContestSubscriptionDto = contestSubscriptionService
    .createContestSubscription(contestId, UUID.randomUUID())
    .let(ContestSubscriptionDtoMapper::map)
}
