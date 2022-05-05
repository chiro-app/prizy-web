package io.prizy.publicapi.port.game.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:13
 */

@GraphQLName("GameBoard")
data class GameBoardDto(
  val id: UUID,
  val name: String,
  val cells: List<Int>,
  val rowSize: Int
)
