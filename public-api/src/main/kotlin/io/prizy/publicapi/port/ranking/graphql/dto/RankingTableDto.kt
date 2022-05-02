package io.prizy.publicapi.port.ranking.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
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
)
