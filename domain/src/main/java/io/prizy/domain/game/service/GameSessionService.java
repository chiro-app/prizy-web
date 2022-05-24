package io.prizy.domain.game.service;

import io.prizy.domain.game.model.GameBoard;
import io.prizy.domain.game.model.GameSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 3:19 PM
 */


@Service
@RequiredArgsConstructor
public class GameSessionService {

  private final GameBoardRandomizer randomizer;

  public GameSession generate(GameBoard board) {
    var startEndPosition = randomizer.randomStartEndPosition(board);
    var obstacles = randomizer.randomObstacles(board, startEndPosition.getLeft(), startEndPosition.getRight());
    return GameSession.builder()
      .score(0L)
      .board(board)
      .obstacles(obstacles)
      .endPosition(startEndPosition.getRight())
      .startPosition(startEndPosition.getLeft())
      .currentPosition(startEndPosition.getLeft())
      .build();
  }

  public Integer applyDelta(Integer position, Pair<Integer, Integer> delta, GameBoard board) {
    return position + delta.getLeft() + (delta.getRight() * board.rowSize());
  }

  public Boolean isValidPosition(Integer position, GameBoard board) {
    var x = position % board.rowSize();
    var y = Math.floorDiv(position, board.rowSize());
    return x >= 0 && x < board.rowSize() && y >= 0 && y < (board.cells().length / board.rowSize());
  }

}
