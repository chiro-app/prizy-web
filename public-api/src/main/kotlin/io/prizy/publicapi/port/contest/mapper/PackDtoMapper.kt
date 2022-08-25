package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.model.Pack
import io.prizy.publicapi.port.contest.graphql.dto.PackDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:05 PM
 */

object PackDtoMapper {

  fun map(dto: Pack): PackDto {
    return when (dto) {
      is Pack.Product -> PackDto.Product(
        id = dto.id,
        name = dto.name,
        lastWinnerPosition = dto.lastWinnerPosition,
        firstWinnerPosition = dto.firstWinnerPosition,
        quantity = dto.quantity,
        value = dto.value.toDouble(),
        assetId = dto.assetId,
      )

      is Pack.Coupon -> PackDto.Coupon(
        id = dto.id,
        name = dto.name,
        lastWinnerPosition = dto.lastWinnerPosition,
        firstWinnerPosition = dto.firstWinnerPosition,
        code = dto.code,
        expiration = dto.expiration
      )

      else -> throw IllegalArgumentException("Unknown pack type ${dto::class.simpleName}")
    }
  }
}
