package io.prizy.publicapi.port.user.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.user.service.UserService
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.user.graphql.dto.UserDto
import io.prizy.publicapi.port.user.mapper.UserDtoMapper
import io.prizy.toolbox.exception.InternalServerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 5/1/2022 10:25 AM
 */

@Component
class UserQuery(private val userService: UserService) : Query {

  @AuthorizedDirective
  suspend fun me(ctx: GraphQLContext.Authenticated): UserDto = withContext(Dispatchers.IO) {
    userService
      .byId(ctx.principal.id)
      .map(UserDtoMapper::map)
      .orElseThrow { InternalServerException() }
  }
}