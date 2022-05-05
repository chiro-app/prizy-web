package io.prizy.publicapi.port.game.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:12
 */

@GraphQLName("CreateGameBoard")
data class CreateGameBoardDto(
  val name: String,
  val cells: List<Int>,
  val rowSize: Int
)
