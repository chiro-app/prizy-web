package io.prizy.publicapi.port.game.mapper

import io.prizy.domain.game.model.GameBoard
import io.prizy.publicapi.port.game.graphql.dto.UpdateGameBoardDto

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:15
 */

object UpdateGameBoardDtoMapper {

  fun map(dto: UpdateGameBoardDto): GameBoard = GameBoard.builder()
    .id(dto.id)
    .name(dto.name)
    .cells(dto.cells.toTypedArray())
    .rowSize(dto.rowSize)
    .build()
}