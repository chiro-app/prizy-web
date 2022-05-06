package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.asset.service.AssetService
import io.prizy.publicapi.port.asset.dto.AssetDto
import io.prizy.publicapi.port.asset.mapper.AssetDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.ServerProperties

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 14:01
 */

@GraphQLName("GameDescription")
data class GameDescriptionDto(
  val id: String,
  val title: String,
  val description: String,
  val usageInstructions: String,
  @GraphQLIgnore
  val iconAssetId: String,
  @GraphQLIgnore
  val coverAssetId: String
) {

  fun url(@Autowired @GraphQLIgnore serverProperties: ServerProperties) = "/game/index.html" // TODO: Implement full url

  suspend fun icon(@GraphQLIgnore @Autowired assetService: AssetService): AssetDto = withContext(Dispatchers.IO) {
    assetService.get(iconAssetId).map(AssetDtoMapper::map).get()
  }

  suspend fun cover(@GraphQLIgnore @Autowired assetService: AssetService): AssetDto = withContext(Dispatchers.IO) {
    assetService.get(coverAssetId).map(AssetDtoMapper::map).get()
  }
}