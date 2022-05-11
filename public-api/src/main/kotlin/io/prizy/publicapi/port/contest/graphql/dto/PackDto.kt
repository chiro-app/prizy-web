package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.asset.port.AssetRepository
import io.prizy.publicapi.port.asset.dto.AssetDto
import io.prizy.publicapi.port.asset.mapper.AssetDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:37 PM
 */

@GraphQLName("Pack")
sealed interface PackDto {

  val id: UUID
  val name: String
  val lastWinnerPosition: Int
  val firstWinnerPosition: Int

  @GraphQLName("Product")
  data class Product(
    override val id: UUID,
    override val name: String,
    override val lastWinnerPosition: Int,
    override val firstWinnerPosition: Int,
    val quantity: Int,
    val value: Float,
    @GraphQLIgnore
    val assetId: String
  ) : PackDto {

    suspend fun asset(@GraphQLIgnore @Autowired assetRepository: AssetRepository): AssetDto =
      withContext(Dispatchers.IO) {
        assetRepository.byId(assetId).map(AssetDtoMapper::map).get()
      }
  }

  @GraphQLName("Coupon")
  data class Coupon(
    override val id: UUID,
    override val name: String,
    override val lastWinnerPosition: Int,
    override val firstWinnerPosition: Int,
    val code: String,
    val expiration: Instant,
  ) : PackDto
}
