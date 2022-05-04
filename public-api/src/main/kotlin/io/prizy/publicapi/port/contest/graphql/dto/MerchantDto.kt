package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.asset.service.AssetService
import io.prizy.publicapi.port.asset.dto.AssetDto
import io.prizy.publicapi.port.asset.mapper.AssetDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:37 PM
 */

@GraphQLName("Merchant")
data class MerchantDto(
  val name: String,
  val siren: String,
  val capital: Float,
  val address: String,
  @GraphQLIgnore
  val logoAssetId: String
) {

  suspend fun logo(@GraphQLIgnore @Autowired assetService: AssetService): AssetDto = withContext(Dispatchers.IO) {
    assetService.get(logoAssetId).map(AssetDtoMapper::map).get()
  }
}
