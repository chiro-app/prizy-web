package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.command.CreatePack
import io.prizy.publicapi.port.contest.graphql.dto.CreatePackDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:56 PM
 */

object CreatePackDtoMapper {

  fun map(dto: CreatePackDto): CreatePack = when {
    dto.coupon != null && dto.product != null -> throw IllegalArgumentException("Cannot create a pack with both coupon and product")
    dto.product != null -> CreatePack.Product.builder()
      .name(dto.product.name)
      .firstWinnerPosition(dto.product.firstWinnerPosition)
      .lastWinnerPosition(dto.product.lastWinnerPosition)
      .quantity(dto.product.quantity)
      .value(dto.product.value)
      .assetId(dto.product.assetId)
      .build()
    dto.coupon != null -> CreatePack.Coupon.builder()
      .name(dto.coupon.name)
      .firstWinnerPosition(dto.coupon.firstWinnerPosition)
      .lastWinnerPosition(dto.coupon.lastWinnerPosition)
      .code(dto.coupon.code)
      .expiration(dto.coupon.expiration)
      .build()
    else -> throw IllegalArgumentException("Cannot create a pack without a coupon or product")
  }
}
