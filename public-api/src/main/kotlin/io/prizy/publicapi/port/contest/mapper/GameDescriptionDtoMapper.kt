package io.prizy.publicapi.port.contest.mapper

import io.prizy.publicapi.application.properties.GameDescriptionProperties
import io.prizy.publicapi.port.contest.graphql.dto.GameDescriptionDto

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 14:03
 */

object GameDescriptionDtoMapper {

  fun map(gameDescriptionProperties: GameDescriptionProperties) = GameDescriptionDto(
    id = gameDescriptionProperties.id,
    title = gameDescriptionProperties.title,
    description = gameDescriptionProperties.description,
    usageInstructions = gameDescriptionProperties.usageInstructions,
    iconAssetId = gameDescriptionProperties.iconAssetId,
    coverAssetId = gameDescriptionProperties.coverAssetId
  )
}