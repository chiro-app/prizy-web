package io.prizy.publicapi.port.resource.graphql

import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import io.prizy.domain.resources.service.ResourceService
import io.prizy.domain.resources.usecase.ClaimContestBonusUseCase
import io.prizy.domain.resources.usecase.ClaimKeysBonusUseCase
import io.prizy.graphql.context.principal
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBalanceDto
import io.prizy.publicapi.port.resource.mapper.ResourceBalanceDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 12:10
 */

@Component
class ResourceMutation(
  private val resourceService: ResourceService,
  private val claimContestBonusUseCase: ClaimContestBonusUseCase,
  private val claimKeysBonusUseCase: ClaimKeysBonusUseCase
) : Mutation {

  @AuthorizedDirective
  suspend fun claimKeysBonus(dfe: DataFetchingEnvironment): Int = withContext(Dispatchers.IO) {
    claimKeysBonusUseCase.claim(dfe.principal().id)
    resourceService
      .getAbsoluteBalance(dfe.principal().id)
      .keys
  }

  @AuthorizedDirective
  suspend fun claimContestBonus(dfe: DataFetchingEnvironment, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      claimContestBonusUseCase.claim(dfe.principal().id, contestId)
      resourceService
        .getContestDependentBalance(
          dfe
            .principal().id, contestId
        )
        .let(ResourceBalanceDtoMapper::map)
    }
}