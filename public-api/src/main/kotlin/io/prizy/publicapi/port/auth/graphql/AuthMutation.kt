package io.prizy.publicapi.port.auth.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.auth.service.PasswordResetService
import io.prizy.domain.user.service.EmailConfirmationService
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.auth.AuthProvider
import io.prizy.publicapi.port.auth.dto.AuthTokenDto
import io.prizy.publicapi.port.auth.dto.LoginDto
import io.prizy.publicapi.port.auth.mapper.AuthTokenDtoMapper
import io.prizy.publicapi.port.user.graphql.dto.EditPasswordDto
import io.prizy.publicapi.port.user.mapper.EditPasswordDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 9:48 PM
 */

@Component
class AuthMutation(
  private val authProvider: AuthProvider,
  private val passwordResetService: PasswordResetService,
  private val emailConfirmationService: EmailConfirmationService
) : Mutation {

  suspend fun login(request: LoginDto): AuthTokenDto = authProvider
    .authenticate(request.login, request.password)
    .let(AuthTokenDtoMapper::map)

  suspend fun refreshCredentials(refreshToken: String): AuthTokenDto = authProvider
    .refreshCredentialS(refreshToken)
    .let(AuthTokenDtoMapper::map)

  suspend fun forgotPassword(loginId: String): Boolean = withContext(Dispatchers.IO) {
    passwordResetService.sendResetCode(loginId)
  }

  suspend fun exchangeResetCodeForToken(code: String): String = withContext(Dispatchers.IO) {
    passwordResetService.exchangeCodeForToken(code)
  }

  suspend fun resetPassword(token: String, newPassword: String): Boolean = withContext(Dispatchers.IO) {
    passwordResetService.resetPassword(token, newPassword)
  }

  @AuthorizedDirective
  suspend fun editPassword(
    ctx: GraphQLContext.Authenticated,
    request: EditPasswordDto
  ): Boolean = withContext(Dispatchers.IO) {
    passwordResetService.editPassword(ctx.principal.id, EditPasswordDtoMapper.map(request))
  }

  suspend fun confirmEmail(code: String): Boolean = withContext(Dispatchers.IO) {
    emailConfirmationService.confirmEmail(code)
  }
}