package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.user.model.User
import io.prizy.publicapi.port.user.graphql.dto.GenderDto
import io.prizy.publicapi.port.user.graphql.dto.UserDto
import io.prizy.publicapi.port.user.graphql.dto.UserStatusDto

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:57 PM
 */

object UserDtoMapper {

  fun map(user: User): UserDto = UserDto(
    id = user.id,
    username = user.username,
    email = user.email,
    firstName = user.firstName,
    lastName = user.lastName,
    mobilePhone = user.mobilePhone.orElse(null),
    addressId = user.addressId.orElse(null),
    gender = GenderDto.valueOf(user.gender.name),
    birthDate = user.birthDate,
    avatarAssetId = user.avatarAssetId.orElse(null),
    status = UserStatusDto.valueOf(user.status.name),
    created = user.created
  )
}