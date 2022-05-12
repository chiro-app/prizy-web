package io.prizy.publicapi.port.ranking

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.ranking.service.RankingService
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.ranking.graphql.dto.RankingTableDto
import io.prizy.publicapi.port.ranking.mapper.RankingTableDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 5/12/2022 9:37 PM
 */

@Component
class RankingQuery(private val rankingService: RankingService) : Query {

  @AuthorizedDirective
  suspend fun rankingTable(contestId: UUID): RankingTableDto? = withContext(Dispatchers.IO) {
    rankingService
      .getForContest(contestId)
      .let(RankingTableDtoMapper::map)
  }
}