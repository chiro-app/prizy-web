package io.prizy.adapters.game.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.game.mapper.GameBoardMapper;
import io.prizy.adapters.game.persistence.repository.GameBoardJpaRepository;
import io.prizy.domain.game.model.GameBoard;
import io.prizy.domain.game.port.GameBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:01
 */


@Component
@RequiredArgsConstructor
public class GameBoardRepositoryImpl implements GameBoardRepository {

  private final GameBoardJpaRepository jpaRepository;

  @Override
  public Boolean existsById(UUID id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public GameBoard save(GameBoard gameBoard) {
    var entity = GameBoardMapper.map(gameBoard);
    return GameBoardMapper.map(jpaRepository.save(entity));
  }

  @Override
  public Collection<GameBoard> list() {
    return jpaRepository.findAll().stream().map(GameBoardMapper::map).toList();
  }

  @Override
  public Optional<GameBoard> byId(UUID id) {
    return jpaRepository.findById(id).map(GameBoardMapper::map);
  }

}
