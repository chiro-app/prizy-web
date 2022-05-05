package io.prizy.publicapi.port.game.mapper

import io.prizy.domain.game.model.GameBoard
import io.prizy.publicapi.port.game.graphql.dto.CreateGameBoardDto

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:15
 */

object CreateGameBoardDtoMapper {

  fun map(dto: CreateGameBoardDto): GameBoard = GameBoard.builder()
    .name(dto.name)
    .cells(dto.cells.toTypedArray())
    .rowSize(dto.rowSize)
    .build()
}