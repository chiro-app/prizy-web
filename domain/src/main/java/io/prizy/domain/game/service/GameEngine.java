package io.prizy.domain.game.service;

import io.prizy.domain.game.model.GameEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Nidhal Dogga
 * @created 5/6/2022 9:42 PM
 */

@Service
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class GameEngine {

  public Flux<GameEvent> startGame(Flux<GameEvent> input) {
    return input;
  }

}
