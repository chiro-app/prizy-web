package io.prizy.domain.game.port;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.game.model.GameBoard;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 12:58
 */

public interface GameBoardRepository {
  Boolean existsById(UUID id);

  GameBoard save(GameBoard gameBoard);

  Collection<GameBoard> list();

  Optional<GameBoard> byId(UUID id);
}
