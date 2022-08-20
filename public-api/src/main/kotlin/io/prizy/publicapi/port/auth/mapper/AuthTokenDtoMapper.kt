package io.prizy.publicapi.port.auth.mapper

import io.prizy.auth.JwtToken
import io.prizy.publicapi.port.auth.dto.AuthTokenDto

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 9:50 PM
 */

object AuthTokenDtoMapper {

  fun map(jwt: JwtToken): AuthTokenDto =
    AuthTokenDto(jwt.token, jwt.refreshToken, jwt.expiresAt, jwt.type)
}