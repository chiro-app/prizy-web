package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.model.Merchant
import io.prizy.publicapi.port.contest.graphql.dto.CreateMerchantDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:04 PM
 */

object CreateMerchantDtoMapper {

  fun map(dto: CreateMerchantDto): Merchant {
    return Merchant.builder()
      .name(dto.name)
      .siren(dto.siren)
      .capital(dto.capital.toFloat())
      .address(dto.address)
      .logoAssetId(dto.logoAssetId)
      .build()
  }
}
