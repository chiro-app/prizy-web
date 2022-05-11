package io.prizy.domain.game.service;

import java.util.UUID;

import io.prizy.domain.game.model.GameEvent;
import io.prizy.domain.game.model.GameEvent.Error.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 5/6/2022 9:42 PM
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GameOrchestrator {

  private final GameEngine gameEngine;

  public GameEvent handleEvent(GameEvent event, UUID userId, String requestId) {
    if (event instanceof GameEvent.GameStarted gameStarted) {
      return gameEngine.startGame(requestId, userId, gameStarted);
    } else if (event instanceof GameEvent.PlayerMoved playerMoved) {
      return gameEngine.movePlayer(requestId, userId, playerMoved);
    }
    return new GameEvent.Error(Code.ILLEGAL_EVENT);
  }

}
