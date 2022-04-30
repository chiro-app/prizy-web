package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.user.model.Gender
import io.prizy.domain.user.model.UpdateUser
import io.prizy.publicapi.port.user.graphql.dto.UpdateUserDto
import java.util.Optional
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:49 PM
 */

object UpdateUserDtoMapper {

  fun map(dto: UpdateUserDto, id: UUID): UpdateUser = UpdateUser.builder()
    .id(id)
    .username(dto.username)
    .email(dto.email)
    .firstName(dto.firstName)
    .lastName(dto.lastName)
    .gender(Gender.valueOf(dto.gender.name))
    .birthDate(dto.birthDate)
    .mobilePhone(Optional.ofNullable(dto.mobilePhone))
    .avatarMediaId(Optional.ofNullable(dto.avatarMediaId))
    .addressId(Optional.ofNullable(dto.addressId))
    .build()
}