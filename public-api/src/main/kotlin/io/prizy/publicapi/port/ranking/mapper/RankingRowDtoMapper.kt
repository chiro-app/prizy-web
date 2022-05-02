package io.prizy.publicapi.port.ranking.mapper

import io.prizy.domain.ranking.model.RankingRow
import io.prizy.publicapi.port.ranking.graphql.dto.RankingRowDto

/**
 *  @author Nidhal Dogga
 *  @created 5/1/2022 11:30 PM
 */

object RankingRowDtoMapper {

  fun map(row: RankingRow): RankingRowDto = RankingRowDto(
    userId = row.userId,
    score = row.score,
    dateTime = row.dateTime
  )
}