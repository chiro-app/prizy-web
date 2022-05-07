package io.prizy.domain.game.service;

import java.util.UUID;

import io.prizy.domain.game.exception.GameEventException;
import io.prizy.domain.game.model.GameEvent;
import io.prizy.domain.game.model.GameEvent.Error.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

/**
 * @author Nidhal Dogga
 * @created 5/6/2022 9:42 PM
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GameOrchestrator {

  private final GameEngine gameEngine;

  public Flux<GameEvent> startGame(Flux<GameEvent> input, UUID userId) {
    Hooks.onOperatorDebug();
    return input
      .flatMap(event -> Mono.deferContextual(ctx -> {
        var requestId = ctx.get(ServerWebExchange.class).getRequest().getId();
        return switch (event) {
          case GameEvent.GameStarted gameStarted -> gameEngine.startGame(requestId, userId, gameStarted);
          case GameEvent.PlayerMoved playerMoved -> gameEngine.movePlayer(requestId, userId, playerMoved);
          default -> Mono.error(new GameEventException(Code.ILLEGAL_EVENT));
        };
      }))
      .handle((event, sink) -> {
        sink.next(event);
        if (event.isFinal()) {
          var requestId = sink.contextView().get(ServerWebExchange.class).getRequest().getId();
          gameEngine.endGame(requestId);
          sink.complete();
        }
      });
  }

}
