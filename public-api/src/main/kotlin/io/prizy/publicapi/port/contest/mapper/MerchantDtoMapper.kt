package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.model.Merchant
import io.prizy.publicapi.port.contest.graphql.dto.MerchantDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:04 PM
 */

object MerchantDtoMapper {

  fun map(dto: MerchantDto): Merchant {
    return Merchant(
      name = dto.name,
      address = dto.address,
      siren = dto.siren,
      logoMediaId = dto.logoMediaId,
      capital = dto.capital,
    )
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
