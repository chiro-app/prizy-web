package io.prizy.publicapi.port.resource.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.resources.service.ResourceBonusService
import io.prizy.domain.resources.service.ResourceBoostService
import io.prizy.domain.resources.service.ResourceService
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
  private val resourceService: ResourceService,
  private val resourceBonusService: ResourceBonusService,
  private val resourceBoostService: ResourceBoostService
) : Query {

  @AuthorizedDirective
  suspend fun keys(ctx: GraphQLContext.Authenticated): Int = withContext(Dispatchers.IO) {
    resourceService.getAbsoluteBalance(ctx.principal.id).keys
  }

  @AuthorizedDirective
  suspend fun contestBalance(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      resourceService
        .getContestDependentBalance(ctx.principal.id, contestId)
        .let(ResourceBalanceDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun hasAvailableContestBonus(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBonusStatusDto =
    withContext(Dispatchers.IO) {
      resourceBonusService
        .availableContestBonus(ctx.principal.id, contestId)
        .let(ResourceBonusStatusDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun hasAvailableKeys(ctx: GraphQLContext.Authenticated): ResourceBonusStatusDto =
    withContext(Dispatchers.IO) {
      resourceBonusService
        .availableKeysBonus(ctx.principal.id)
        .let(ResourceBonusStatusDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun availableContestBonus(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto? =
    withContext(Dispatchers.IO) {
      resourceBonusService
        .getAvailableContestBonus(ctx.principal.id, contestId)
        .map(ResourceBalanceDtoMapper::map)
        .orElse(null)
    }

  @AuthorizedDirective
  suspend fun boost(ctx: GraphQLContext.Authenticated, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      resourceBoostService
        .boost(ctx.principal.id, contestId)
        .let(ResourceBalanceDtoMapper::map)
    }
}