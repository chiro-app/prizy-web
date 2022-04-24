package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.command.CreatePack
import io.prizy.publicapi.port.contest.graphql.dto.CreatePackDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:56 PM
 */

object CreatePackMapper {

  fun map(dto: CreatePackDto): CreatePack = when {
    dto.coupon != null && dto.product != null -> throw IllegalArgumentException("Cannot create a pack with both coupon and product")
    dto.product != null -> CreatePack.Product(
      dto.product.name,
      dto.product.firstWinnerPosition,
      dto.product.lastWinnerPosition,
      dto.product.quantity,
      dto.product.value,
      dto.product.mediaId
    )
    dto.coupon != null -> CreatePack.Coupon(
      dto.coupon.name,
      dto.coupon.firstWinnerPosition,
      dto.coupon.lastWinnerPosition,
      dto.coupon.code,
      dto.coupon.expiration
    )
    else -> throw IllegalArgumentException("Cannot create a pack without a coupon or product")
  }
}
