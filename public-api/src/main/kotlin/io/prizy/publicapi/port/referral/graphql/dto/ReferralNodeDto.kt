package io.prizy.publicapi.port.referral.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.user.service.UserService
import io.prizy.publicapi.port.user.graphql.dto.UserDto
import io.prizy.publicapi.port.user.mapper.UserDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:10 PM
 */

@GraphQLName("ReferralNode")
data class ReferralNodeDto(
  val id: UUID,
  val confirmed: Boolean,
) {

  private lateinit var user: UserDto

  suspend fun firstName(@GraphQLIgnore @Autowired userService: UserService): String {
    if (!this::user.isInitialized) {
      fetchUser(userService)
    }
    return user.firstName
  }

  suspend fun lastName(@GraphQLIgnore @Autowired userService: UserService): String {
    if (!this::user.isInitialized) {
      fetchUser(userService)
    }
    return user.lastName
  }

  @GraphQLIgnore
  private suspend fun fetchUser(userService: UserService) = withContext(Dispatchers.IO) {
    user = userService
      .getUser(id)
      .map(UserDtoMapper::map)
      .get()
  }
}
