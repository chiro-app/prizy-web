package io.prizy.publicapi.port.reward.mapper

import io.prizy.domain.reward.domain.Reward
import io.prizy.publicapi.port.reward.graphql.dto.RewardDto

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 15:42
 */

object RewardDtoMapper {

  fun map(reward: Reward) = RewardDto(
    id = reward.id,
    packId = reward.packId,
    userId = reward.userId,
    created = reward.created,
  )
}