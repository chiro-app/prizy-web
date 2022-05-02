package io.prizy.publicapi.port.ranking.mapper

import io.prizy.domain.ranking.model.RankingTable
import io.prizy.publicapi.port.ranking.graphql.dto.RankingTableDto

/**
 *  @author Nidhal Dogga
 *  @created 5/1/2022 11:30 PM
 */

object RankingTableDtoMapper {

  fun map(table: RankingTable): RankingTableDto {
    return RankingTableDto(
      contestId = table.contestId,
      rows = table.rows.map(RankingRowDtoMapper::map)
    )
  }
}