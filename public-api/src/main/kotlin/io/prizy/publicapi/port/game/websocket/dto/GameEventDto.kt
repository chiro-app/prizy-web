package io.prizy.publicapi.port.game.websocket.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import io.prizy.domain.game.model.GameBoard
import io.prizy.domain.game.model.GameEvent.Error.Code
import io.prizy.domain.game.model.GameEvent.PlayerMoved.Direction
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 5/6/2022 9:35 PM
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface GameEventDto {

  @JsonTypeName("error")
  data class Error(val errorCode: Code) : GameEventDto

  @JsonTypeName("game_lost")
  data class GameLost(val obstacles: Collection<Int>) : GameEventDto

  @JsonTypeName("game_won")
  data class GameWon(val score: Long, val obstacles: Collection<Int>) : GameEventDto

  @JsonTypeName("game_started")
  data class GameStarted(val contestId: UUID, val diamonds: Long) : GameEventDto

  @JsonTypeName("player_moved")
  data class PlayerMoved(val direction: Direction) : GameEventDto

  @JsonTypeName("board_retrieved")
  data class BoardRetrieved(
    val board: GameBoard,
    val startPosition: Int,
    val endPosition: Int,
    val obstacles: Collection<Int>
  ) : GameEventDto

  @JsonTypeName("score_updated")
  data class ScoreUpdated(val score: Long) : GameEventDto
}