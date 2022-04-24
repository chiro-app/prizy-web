package io.prizy.adapters.contest.mapper

import io.prizy.adapters.contest.persistence.entity.PackEntity
import io.prizy.domain.contest.model.Pack

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:30 PM
 */

object PackMapper {

  fun map(entity: PackEntity): Pack {
    return when (entity) {
      is PackEntity.Product -> Pack.Product(
        id = entity.id ?: throw IllegalStateException("Tried to map a product pack without an id"),
        name = entity.name,
        lastWinnerPosition = entity.lastWinnerPosition,
        firstWinnerPosition = entity.firstWinnerPosition,
        quantity = entity.quantity,
        value = entity.value,
        mediaId = entity.mediaId,
      )
      is PackEntity.Coupon -> Pack.Coupon(
        id = entity.id ?: throw IllegalStateException("Tried to map a coupon pack without an id"),
        name = entity.name,
        lastWinnerPosition = entity.lastWinnerPosition,
        firstWinnerPosition = entity.firstWinnerPosition,
        code = entity.code,
        expiration = entity.expiration
      )
    }
  }

  fun map(entity: Pack): PackEntity {
    return when (entity) {
      is Pack.Product -> PackEntity.Product(
        id = entity.id,
        name = entity.name,
        lastWinnerPosition = entity.lastWinnerPosition,
        firstWinnerPosition = entity.firstWinnerPosition,
        quantity = entity.quantity,
        value = entity.value,
        mediaId = entity.mediaId,
      )
      is Pack.Coupon -> PackEntity.Coupon(
        id = entity.id,
        name = entity.name,
        lastWinnerPosition = entity.lastWinnerPosition,
        firstWinnerPosition = entity.firstWinnerPosition,
        code = entity.code,
        expiration = entity.expiration
      )
    }
  }
}
