package io.prizy.publicapi.port.contest.mapper

import io.prizy.publicapi.application.properties.GameProperties
import io.prizy.publicapi.port.contest.graphql.dto.GameDescriptionDto

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 14:03
 */

object GameDescriptionDtoMapper {

  fun map(gameProperties: GameProperties) = GameDescriptionDto(
    id = gameProperties.id,
    title = gameProperties.title,
    description = gameProperties.description,
    usageInstructions = gameProperties.usageInstructions,
    iconAssetId = gameProperties.iconAssetId,
    coverAssetId = gameProperties.coverAssetId
  )
}