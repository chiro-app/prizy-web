package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.user.model.Address
import io.prizy.publicapi.port.user.graphql.dto.AddressDto

object AddressDtoMapper {

  fun map(address: Address): AddressDto {
    return AddressDto(
      street = address.street(),
      city = address.city(),
      country = address.country(),
      postalCode = address.postalCode,
      extraLine1 = address.extraLine1().orElse(null),
      extraLine2 = address.extraLine2().orElse(null)
    )
  }
}
