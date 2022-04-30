package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.model.Merchant
import io.prizy.publicapi.port.contest.graphql.dto.MerchantDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:04 PM
 */

object MerchantDtoMapper {

  fun map(dto: MerchantDto): Merchant {
    return Merchant.builder()
      .name(dto.name)
      .siren(dto.siren)
      .capital(dto.capital)
      .address(dto.address)
      .logoMediaId(dto.logoMediaId)
      .build()
  }

  fun map(dto: Merchant): MerchantDto {
    return MerchantDto(
      name = dto.name,
      address = dto.address,
      siren = dto.siren,
      logoMediaId = dto.logoMediaId,
      capital = dto.capital,
    )
  }
}
