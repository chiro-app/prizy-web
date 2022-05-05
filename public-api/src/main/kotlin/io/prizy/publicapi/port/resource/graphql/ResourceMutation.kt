package io.prizy.publicapi.port.resource.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.resources.service.ResourceBonusService
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 12:10
 */

@Component
class ResourceMutation(
  private val resourceBonusService: ResourceBonusService
) : Mutation {

  companion object {
    val log = LoggerFactory.getLogger(ResourceMutation::class.java)
  }

  @AuthorizedDirective
  suspend fun claimKeysBonus(ctx: GraphQLContext.Authenticated): Boolean = withContext(Dispatchers.IO) {
    try {
      resourceBonusService.claimKeysDailyBonus(ctx.principal.id)
      true
    } catch (throwable: Throwable) {
      log.warn(throwable.message, throwable)
      false
    }
  }

  @AuthorizedDirective
  suspend fun claimContestBonus(ctx: GraphQLContext.Authenticated, contestId: UUID): Boolean =
    withContext(Dispatchers.IO) {
      try {
        resourceBonusService.claimContestDailyBonus(ctx.principal.id, contestId)
        true
      } catch (throwable: Throwable) {
        log.warn(throwable.message, throwable)
        false
      }
    }
}