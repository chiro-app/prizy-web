package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.user.model.CreateUser
import io.prizy.domain.user.model.Gender
import io.prizy.publicapi.port.user.graphql.dto.CreateUserDto
import java.util.Optional

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:49 PM
 */

object CreateUserDtoMapper {

  fun map(dto: CreateUserDto): CreateUser = CreateUser.builder()
    .username(dto.username)
    .email(dto.email)
    .password(dto.password)
    .firstName(dto.firstName)
    .lastName(dto.lastName)
    .gender(Gender.valueOf(dto.gender.name))
    .birthDate(dto.birthDate)
    .mobilePhone(Optional.ofNullable(dto.mobilePhone))
    .build()
}