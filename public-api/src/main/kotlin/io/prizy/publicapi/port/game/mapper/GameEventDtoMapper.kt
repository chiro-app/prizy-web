package io.prizy.publicapi.port.game.mapper

import io.prizy.domain.game.model.GameEvent
import io.prizy.publicapi.port.game.websocket.dto.GameEventDto

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 9:59 PM
 */

object GameEventDtoMapper {

  fun map(dto: GameEventDto): GameEvent = when (dto) {
    is GameEventDto.GameStarted -> GameEvent.GameStarted(dto.contestId, dto.diamonds)
    is GameEventDto.GameLost -> GameEvent.GameLost(dto.obstacles)
    is GameEventDto.GameWon -> GameEvent.GameWon(dto.obstacles, dto.score)
    is GameEventDto.BoardRetrieved -> GameEvent.BoardRetrieved(dto.board, dto.startPosition, dto.endPosition)
    is GameEventDto.Error -> GameEvent.Error(dto.errorCode)
    is GameEventDto.PlayedMoved -> GameEvent.PlayedMoved(dto.direction)
    is GameEventDto.ScoreUpdated -> GameEvent.ScoreUpdated(dto.score)
  }
}