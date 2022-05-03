package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
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
    val assetId: String
  ) : PackDto

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
