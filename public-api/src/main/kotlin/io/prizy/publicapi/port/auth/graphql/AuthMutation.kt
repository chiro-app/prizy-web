package io.prizy.publicapi.port.auth.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.publicapi.auth.AuthProvider
import io.prizy.publicapi.port.auth.dto.AuthTokenDto
import io.prizy.publicapi.port.auth.dto.LoginDto
import io.prizy.publicapi.port.auth.mapper.AuthTokenDtoMapper
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 9:48 PM
 */

@Component
class AuthMutation(private val authProvider: AuthProvider) : Mutation {

  suspend fun login(request: LoginDto): AuthTokenDto = authProvider
    .authenticate(request.login, request.password)
    .let(AuthTokenDtoMapper::map)

  suspend fun refreshCredentials(refreshToken: String): AuthTokenDto = authProvider
    .refreshCredentialS(refreshToken)
    .let(AuthTokenDtoMapper::map)
}