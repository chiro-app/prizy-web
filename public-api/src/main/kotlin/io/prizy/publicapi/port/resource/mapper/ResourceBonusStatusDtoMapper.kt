package io.prizy.publicapi.port.resource.mapper

import io.prizy.domain.resources.model.ResourceBonusStatus
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBonusStatusDto

/**
 *  @author Nidhal Dogga
 *  @created 5/12/2022 8:18 PM
 */

object ResourceBonusStatusDtoMapper {

  fun map(bonusStatus: ResourceBonusStatus) = ResourceBonusStatusDto(
    bonusStatus.available,
    bonusStatus.availableAt.orElse(null)
  )
}