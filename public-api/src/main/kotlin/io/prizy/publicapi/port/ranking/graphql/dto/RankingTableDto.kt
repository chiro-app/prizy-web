package io.prizy.publicapi.port.ranking.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.user.service.UserService
import io.prizy.publicapi.port.user.graphql.dto.UserDto
import io.prizy.publicapi.port.user.mapper.UserDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 5/1/2022 11:28 PM
 */

@GraphQLName("RankingTable")
data class RankingTableDto(
  @GraphQLIgnore
  val contestId: UUID,
  val rows: List<RankingRowDto>
)

@GraphQLName("RankingRow")
data class RankingRowDto(
  @GraphQLIgnore
  val userId: UUID,
  val score: Long,
  val dateTime: Instant
) {

  suspend fun user(@GraphQLIgnore @Autowired userService: UserService): UserDto = withContext(Dispatchers.IO) {
    userService.getUser(userId).map(UserDtoMapper::map).get()
  }
}
