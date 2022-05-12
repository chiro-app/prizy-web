package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.contest.service.ContestService
import io.prizy.publicapi.port.contest.mapper.ContestDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:37 PM
 */

@GraphQLName("ContestSubscription")
data class ContestSubscriptionDto(
  val id: UUID,
  @GraphQLIgnore
  val userId: UUID,
  @GraphQLIgnore
  val contestId: UUID,
  val dateTime: Instant
) {

  suspend fun contest(@GraphQLIgnore @Autowired contestService: ContestService): ContestDto =
    withContext(Dispatchers.IO) {
      contestService.byId(contestId).map(ContestDtoMapper::map).get()
    }
}
