package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.auth.model.EditPassword
import io.prizy.publicapi.port.user.graphql.dto.EditPasswordDto

/**
 *  @author Nidhal Dogga
 *  @created 5/1/2022 11:56 AM
 */

object EditPasswordDtoMapper {

  fun map(dto: EditPasswordDto): EditPassword {
    return EditPassword.builder()
      .oldPassword(dto.oldPassword)
      .newPassword(dto.newPassword)
      .build()
  }
}