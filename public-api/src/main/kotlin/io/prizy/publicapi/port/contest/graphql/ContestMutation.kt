package io.prizy.publicapi.port.contest.graphql

import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.contest.usecase.CreateContestUseCase
import io.prizy.domain.contest.usecase.SubscribeToContestUseCase
import io.prizy.domain.contest.usecase.UpdateContestUseCase
import io.prizy.graphql.context.principal
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.contest.graphql.dto.ContestDto
import io.prizy.publicapi.port.contest.graphql.dto.ContestSubscriptionDto
import io.prizy.publicapi.port.contest.graphql.dto.CreateContestDto
import io.prizy.publicapi.port.contest.graphql.dto.UpdateContestDto
import io.prizy.publicapi.port.contest.mapper.ContestDtoMapper
import io.prizy.publicapi.port.contest.mapper.ContestSubscriptionDtoMapper
import io.prizy.publicapi.port.contest.mapper.CreateContestDtoMapper
import io.prizy.publicapi.port.contest.mapper.UpdateContestMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:26 PM
 */

@Component
class ContestMutation(
  private val createContestUseCase: CreateContestUseCase,
  private val updateContestUseCase: UpdateContestUseCase,
  private val subscribeToContestUseCase: SubscribeToContestUseCase,
) : Mutation {

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun createContest(request: CreateContestDto): ContestDto = withContext(Dispatchers.IO) {
    createContestUseCase
      .create(CreateContestDtoMapper.map(request))
      .let(ContestDtoMapper::map)
  }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun updateContest(request: UpdateContestDto): ContestDto = withContext(Dispatchers.IO) {
    updateContestUseCase
      .update(UpdateContestMapper.map(request))
      .let(ContestDtoMapper::map)
  }

  @AuthorizedDirective
  suspend fun createContestSubscription(dfe: DataFetchingEnvironment, contestId: UUID): ContestSubscriptionDto =
    withContext(Dispatchers.IO) {
      subscribeToContestUseCase
        .subscribe(contestId, dfe.principal().id)
        .let(ContestSubscriptionDtoMapper::map)
    }
}
