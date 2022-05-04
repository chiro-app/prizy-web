package io.prizy.publicapi.port.reward.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.user.service.UserService
import io.prizy.publicapi.port.contest.graphql.dto.PackDto
import io.prizy.publicapi.port.contest.mapper.PackDtoMapper
import io.prizy.publicapi.port.user.graphql.dto.UserDto
import io.prizy.publicapi.port.user.mapper.UserDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 15:39
 */

@GraphQLName("Reward")
data class RewardDto(
  val id: UUID,
  @GraphQLIgnore
  val userId: UUID,
  @GraphQLIgnore
  val packId: UUID,
  val created: Instant
) {

  suspend fun user(@GraphQLIgnore @Autowired userService: UserService): UserDto = withContext(Dispatchers.IO) {
    userService.byId(userId).map(UserDtoMapper::map).get()
  }

  suspend fun pack(@GraphQLIgnore @Autowired contestService: ContestService): PackDto = withContext(Dispatchers.IO) {
    contestService.packById(packId).map(PackDtoMapper::map).get()
  }
}
