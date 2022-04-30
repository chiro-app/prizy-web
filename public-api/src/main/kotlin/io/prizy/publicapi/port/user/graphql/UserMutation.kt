package io.prizy.publicapi.port.user.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.user.service.UserService
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.user.graphql.dto.CreateUserDto
import io.prizy.publicapi.port.user.graphql.dto.UpdateUserDto
import io.prizy.publicapi.port.user.graphql.dto.UserDto
import io.prizy.publicapi.port.user.mapper.CreateUserDtoMapper
import io.prizy.publicapi.port.user.mapper.UpdateUserDtoMapper
import io.prizy.publicapi.port.user.mapper.UserDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:43 PM
 */

@Component
class UserMutation(private val userService: UserService) : Mutation {

  suspend fun createUser(request: CreateUserDto): UserDto = withContext(Dispatchers.IO) {
    userService
      .createUser(CreateUserDtoMapper.map(request))
      .let(UserDtoMapper::map)
  }

  @AuthorizedDirective
  suspend fun updateUser(ctx: GraphQLContext.Authenticated, request: UpdateUserDto): UserDto =
    withContext(Dispatchers.IO) {
      userService
        .updateUser(UpdateUserDtoMapper.map(request, ctx.principal.id))
        .let(UserDtoMapper::map)
    }
}