package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.user.model.Address
import io.prizy.publicapi.port.user.graphql.dto.UpdateAddressDto
import java.util.Optional

object UpdateAddressDtoMapper {

  fun map(address: UpdateAddressDto): Address = Address.builder()
    .street(address.street)
    .city(address.city)
    .country(address.country)
    .postalCode(address.postalCode)
    .extraLine1(Optional.ofNullable(address.extraLine1))
    .extraLine2(Optional.ofNullable(address.extraLine2))
    .build()
}