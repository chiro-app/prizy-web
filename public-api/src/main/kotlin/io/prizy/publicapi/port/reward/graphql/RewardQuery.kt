package io.prizy.publicapi.port.reward.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.reward.service.RewardService
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.reward.graphql.dto.RewardDto
import io.prizy.publicapi.port.reward.mapper.RewardDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 15:38
 */

@Component
class RewardQuery(private val rewardService: RewardService) : Query {

  @AuthorizedDirective
  suspend fun rewards(ctx: GraphQLContext.Authenticated): List<RewardDto> = withContext(Dispatchers.IO) {
    rewardService.userRewards(ctx.principal.id).map(RewardDtoMapper::map)
  }

  suspend fun rewardsByAccessCode(accessCode: String): List<RewardDto> = withContext(Dispatchers.IO) {
    rewardService.contestRewardsByAccessCode(accessCode).map(RewardDtoMapper::map)
  }
}