package io.prizy.domain.game.exception;

import java.util.UUID;

import io.prizy.toolbox.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:36
 */


@RequiredArgsConstructor
@ErrorCode("GAME_BOARD_NOT_FOUND")
public class GameBoardNotFoundException extends RuntimeException {
  private final UUID id;
}
