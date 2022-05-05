package io.prizy.publicapi.port.resource.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.contest.exception.ContestNotFoundException
import io.prizy.domain.resources.service.ResourceBonusService
import io.prizy.domain.resources.service.ResourceBoostService
import io.prizy.domain.resources.service.ResourceService
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBalanceDto
import io.prizy.publicapi.port.resource.mapper.ResourceBalanceDtoMapper
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
  private val resourceService: ResourceService,
  private val resourceBonusService: ResourceBonusService,
  private val resourceBoostService: ResourceBoostService
) : Query {

  @AuthorizedDirective
  suspend fun keys(ctx: GraphQLContext.Authenticated): Int = withContext(Dispatchers.IO) {
    resourceService.getAbsoluteBalance(ctx.principal.id).keys
  }

  @AuthorizedDirective
  suspend fun availableBonus(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      resourceBonusService
        .getAvailableContestBonus(ctx.principal.id, contestId)
        .map(ResourceBalanceDtoMapper::map)
        .orElseThrow { ContestNotFoundException(contestId) }
    }

  @AuthorizedDirective
  suspend fun hasAvailableBonus(ctx: GraphQLContext.Authenticated, contestId: UUID): Boolean =
    withContext(Dispatchers.IO) {
      resourceBonusService.hasAvailableContestBonus(ctx.principal.id, contestId)
    }

  @AuthorizedDirective
  suspend fun balance(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      resourceService.getContestDependentBalance(ctx.principal.id, contestId).let(ResourceBalanceDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun boost(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      resourceBoostService.boost(ctx.principal.id, contestId).let(ResourceBalanceDtoMapper::map)
    }
}