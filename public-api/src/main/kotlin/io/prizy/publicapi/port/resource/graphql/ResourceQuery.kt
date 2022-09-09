package io.prizy.publicapi.port.resource.graphql

import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import io.prizy.domain.resources.usecase.GetAvailableContestBonusUseCase
import io.prizy.domain.resources.usecase.GetContestBalanceUseCase
import io.prizy.domain.resources.usecase.GetContestBonusStatusUseCase
import io.prizy.domain.resources.usecase.GetContestBoostUseCase
import io.prizy.domain.resources.usecase.GetKeysBalanceUseCase
import io.prizy.domain.resources.usecase.GetKeysBonusStatusUseCase
import io.prizy.graphql.context.principal
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBalanceDto
import io.prizy.publicapi.port.resource.graphql.dto.ResourceBonusStatusDto
import io.prizy.publicapi.port.resource.mapper.ResourceBalanceDtoMapper
import io.prizy.publicapi.port.resource.mapper.ResourceBonusStatusDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 12:10
 */

@Component
class ResourceQuery(
  private val getKeysBalanceUseCase: GetKeysBalanceUseCase,
  private val getContestBalanceUseCase: GetContestBalanceUseCase,
  private val getContestBoostUseCase: GetContestBoostUseCase,
  private val getAvailableContestBonusUseCase: GetAvailableContestBonusUseCase,
  private val getContestBonusStatusUseCase: GetContestBonusStatusUseCase,
  private val getKeysBonusStatusUseCase: GetKeysBonusStatusUseCase
) : Query {

  @AuthorizedDirective
  suspend fun keys(dfe: DataFetchingEnvironment): Int = withContext(Dispatchers.IO) {
    getKeysBalanceUseCase.get(dfe.principal().id).keys
  }

  @AuthorizedDirective
  suspend fun contestBalance(dfe: DataFetchingEnvironment, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      getContestBalanceUseCase
        .get(dfe.principal().id, contestId)
        .let(ResourceBalanceDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun hasAvailableContestBonus(dfe: DataFetchingEnvironment, contestId: UUID): ResourceBonusStatusDto =
    withContext(Dispatchers.IO) {
      getContestBonusStatusUseCase
        .get(dfe.principal().id, contestId)
        .let(ResourceBonusStatusDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun hasAvailableKeys(dfe: DataFetchingEnvironment): ResourceBonusStatusDto =
    withContext(Dispatchers.IO) {
      getKeysBonusStatusUseCase
        .get(dfe.principal().id)
        .let(ResourceBonusStatusDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun availableContestBonus(dfe: DataFetchingEnvironment, contestId: UUID): ResourceBalanceDto? =
    withContext(Dispatchers.IO) {
      getAvailableContestBonusUseCase
        .get(dfe.principal().id, contestId)
        .map(ResourceBalanceDtoMapper::map)
        .orElse(null)
    }

  @AuthorizedDirective
  suspend fun boost(dfe: DataFetchingEnvironment, contestId: UUID): ResourceBalanceDto =
    withContext(Dispatchers.IO) {
      getContestBoostUseCase
        .get(dfe.principal().id, contestId)
        .let(ResourceBalanceDtoMapper::map)
    }
}