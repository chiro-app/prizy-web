package io.prizy.adapters.game.mapper;

import io.prizy.adapters.game.persistence.entity.GameBoardEntity;
import io.prizy.domain.game.model.GameBoard;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:07
 */


@UtilityClass
public class GameBoardMapper {

  public GameBoard map(GameBoardEntity entity) {
    return GameBoard.builder()
      .id(entity.getId())
      .name(entity.getName())
      .cells(entity.getCells())
      .rowSize(entity.getRowSize())
      .build();
  }

  public GameBoardEntity map(GameBoard gameBoard) {
    return GameBoardEntity.builder()
      .id(gameBoard.id())
      .name(gameBoard.name())
      .cells(gameBoard.cells())
      .rowSize(gameBoard.rowSize())
      .build();
  }

}
