package io.prizy.publicapi.port.reward.graphql

import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import io.prizy.domain.reward.service.RewardService
import io.prizy.graphql.context.principal
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
  suspend fun rewards(dfe: DataFetchingEnvironment): List<RewardDto> = withContext(Dispatchers.IO) {
    rewardService.userRewards(dfe.principal().id).map(RewardDtoMapper::map)
  }

  suspend fun rewardsByAccessCode(accessCode: String): List<RewardDto> = withContext(Dispatchers.IO) {
    rewardService.contestRewardsByAccessCode(accessCode).map(RewardDtoMapper::map)
  }
}