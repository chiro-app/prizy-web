package io.prizy.publicapi.port.contest.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.contest.service.ContestService
import io.prizy.publicapi.port.contest.graphql.dto.ContestDto
import io.prizy.publicapi.port.contest.graphql.dto.CreateContestDto
import io.prizy.publicapi.port.contest.graphql.dto.UpdateContestDto
import io.prizy.publicapi.port.contest.mapper.ContestDtoMapper
import io.prizy.publicapi.port.contest.mapper.CreateContestMapper
import io.prizy.publicapi.port.contest.mapper.UpdateContestMapper
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:26 PM
 */

@Component
class ContestMutation(private val contestService: ContestService) : Mutation {

  suspend fun createContest(request: CreateContestDto): ContestDto = contestService
    .createContest(CreateContestMapper.map(request))
    .let(ContestDtoMapper::map)

  suspend fun updateContest(request: UpdateContestDto): ContestDto = contestService
    .updateContest(UpdateContestMapper.map(request))
    .let(ContestDtoMapper::map)
}
