package io.prizy.publicapi.port.contest.graphql

import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.contest.usecase.GetUserSubscribedReferralsUseCase
import io.prizy.graphql.context.principal
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.contest.graphql.dto.ContestDto
import io.prizy.publicapi.port.contest.mapper.ContestDtoMapper
import io.prizy.publicapi.port.contest.mapper.ReferralDtoMapper
import io.prizy.publicapi.port.referral.graphql.dto.ReferralNodeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 2:18 PM
 */

@Component
class ContestQuery(
  private val contestService: ContestService,
  private val getUserSubscribedReferralsUseCase: GetUserSubscribedReferralsUseCase
) : Query {

  suspend fun contestById(id: UUID): ContestDto? = withContext(Dispatchers.IO) {
    contestService.byId(id).map(ContestDtoMapper::map).orElse(null)
  }

  suspend fun contests(): List<ContestDto> = withContext(Dispatchers.IO) {
    contestService.contests().map(ContestDtoMapper::map)
  }

  suspend fun listAllContests(): List<ContestDto> = withContext(Dispatchers.IO) {
    contestService.listAllContests().map(ContestDtoMapper::map)
  }

  @AuthorizedDirective
  suspend fun subscribedReferrals(
    dfe: DataFetchingEnvironment,
    contestId: UUID
  ): List<ReferralNodeDto> = withContext(Dispatchers.IO) {
    getUserSubscribedReferralsUseCase
      .get(contestId, dfe.principal().id)
      .map(ReferralDtoMapper::map)
  }
}
