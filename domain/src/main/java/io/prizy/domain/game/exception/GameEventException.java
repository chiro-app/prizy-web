package io.prizy.domain.game.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.prizy.domain.game.model.GameEvent.Error.Code;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 1:34 PM
 */


@Getter
@RequiredArgsConstructor
public class GameEventException extends RuntimeException {
  private final Code code;
}
