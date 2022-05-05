package io.prizy.publicapi.port.resource.mapper

import io.prizy.domain.resources.model.ResourceBalance
import io.prizy.domain.resources.model.ResourceBoost
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBalanceDto

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 12:15
 */

object ResourceBalanceDtoMapper {

  fun map(balance: ResourceBalance.ContestDependent) = ResourceBalanceDto(
    lives = balance.lives,
    diamonds = balance.diamonds
  )

  fun map(boost: ResourceBoost) = ResourceBalanceDto(
    lives = boost.lives,
    diamonds = boost.diamonds
  )
}