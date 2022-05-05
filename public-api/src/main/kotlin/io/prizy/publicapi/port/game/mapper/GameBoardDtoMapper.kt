package io.prizy.publicapi.port.game.mapper

import io.prizy.domain.game.model.GameBoard
import io.prizy.publicapi.port.game.graphql.dto.GameBoardDto

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:15
 */

object GameBoardDtoMapper {

  fun map(board: GameBoard): GameBoardDto = GameBoardDto(
    id = board.id,
    name = board.name,
    cells = board.cells.asList(),
    rowSize = board.rowSize,
  )
}