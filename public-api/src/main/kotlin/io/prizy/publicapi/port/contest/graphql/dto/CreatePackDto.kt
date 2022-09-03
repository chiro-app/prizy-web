package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.Instant

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:43 PM
 */

@GraphQLName("CreatePack")
data class CreatePackDto(
  val coupon: Coupon?,
  val product: Product?,
) {

  @GraphQLName("CreateProduct")
  data class Product(
    val name: String,
    val firstWinnerPosition: Int,
    val lastWinnerPosition: Int,
    val quantity: Int,
    val value: Double,
    val assetId: String,
  )

  @GraphQLName("CreateCoupon")
  data class Coupon(
    val name: String,
    val firstWinnerPosition: Int,
    val lastWinnerPosition: Int,
    val code: String,
    val expiration: Instant,
  )
}
