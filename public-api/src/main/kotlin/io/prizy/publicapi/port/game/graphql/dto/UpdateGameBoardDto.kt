package io.prizy.publicapi.port.game.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:12
 */

@GraphQLName("UpdateGameBoard")
data class UpdateGameBoardDto(
  val id: UUID,
  val name: String,
  val cells: List<Int>,
  val rowSize: Int
)
