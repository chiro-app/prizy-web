package io.prizy.domain.game.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.game.exception.GameBoardNotFoundException;
import io.prizy.domain.game.model.GameBoard;
import io.prizy.domain.game.port.GameBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 12:56
 */


@Transactional
@RequiredArgsConstructor
public class GameBoardService {

  private final GameBoardRepository repository;

  public GameBoard create(GameBoard board) {
    return repository.save(board);
  }

  public Optional<GameBoard> byId(UUID id) {
    return repository.byId(id);
  }

  public GameBoard update(GameBoard board) {
    if (!repository.existsById(board.id())) {
      throw new GameBoardNotFoundException(board.id());
    }
    return repository.save(board);
  }

  public Collection<GameBoard> list() {
    return repository.list();
  }

}
