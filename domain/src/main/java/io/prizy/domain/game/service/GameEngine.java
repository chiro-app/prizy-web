package io.prizy.domain.game.service;

import io.prizy.domain.game.model.GameEvent;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * @author Nidhal Dogga
 * @created 5/6/2022 9:42 PM
 */


@RequiredArgsConstructor
public class GameEngine {

  public Flux<GameEvent> startGame(Flux<GameEvent> input) {
    return input;
  }

}
