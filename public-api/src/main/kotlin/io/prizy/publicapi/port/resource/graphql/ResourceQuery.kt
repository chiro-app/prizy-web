package io.prizy.publicapi.port.resource.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.resources.usecase.GetAvailableContestBonusUseCase
import io.prizy.domain.resources.usecase.GetContestBalanceUseCase
import io.prizy.domain.resources.usecase.GetContestBonusStatusUseCase
import io.prizy.domain.resources.usecase.GetContestBoostUseCase
import io.prizy.domain.resources.usecase.GetKeysBalanceUseCase
import io.prizy.domain.resources.usecase.GetKeysBonusStatusUseCase
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBalanceDto
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBonusStatusDto
import io.prizy.publicapi.port.resource.mapper.ResourceBalanceDtoMapper
import io.prizy.publicapi.port.resource.mapper.ResourceBonusStatusDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 12:10
 */

@Component
class ResourceQuery(
  private val getKeysBalanceUseCase: GetKeysBalanceUseCase,
  private val getContestBalanceUseCase: GetContestBalanceUseCase,
  private val getContestBoostUseCase: GetContestBoostUseCase,
  private val getAvailableContestBonusUseCase: GetAvailableContestBonusUseCase,
  private val getContestBonusStatusUseCase: GetContestBonusStatusUseCase,
  private val getKeysBonusStatusUseCase: GetKeysBonusStatusUseCase
) : Query {

  @AuthorizedDirective
  suspend fun keys(ctx: GraphQLContext.Authenticated): Int = withContext(Dispatchers.IO) {
    getKeysBalanceUseCase.get(ctx.principal.id).keys
  }

  @AuthorizedDirective
  suspend fun contestBalance(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      getContestBalanceUseCase
        .get(ctx.principal.id, contestId)
        .let(ResourceBalanceDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun hasAvailableContestBonus(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBonusStatusDto =
    withContext(Dispatchers.IO) {
      getContestBonusStatusUseCase
        .get(ctx.principal.id, contestId)
        .let(ResourceBonusStatusDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun hasAvailableKeys(ctx: GraphQLContext.Authenticated): ResourceBonusStatusDto =
    withContext(Dispatchers.IO) {
      getKeysBonusStatusUseCase
        .get(ctx.principal.id)
        .let(ResourceBonusStatusDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun availableContestBonus(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto? =
    withContext(Dispatchers.IO) {
      getAvailableContestBonusUseCase
        .get(ctx.principal.id, contestId)
        .map(ResourceBalanceDtoMapper::map)
        .orElse(null)
    }

  @AuthorizedDirective
  suspend fun boost(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      getContestBoostUseCase
        .get(ctx.principal.id, contestId)
        .let(ResourceBalanceDtoMapper::map)
    }
}