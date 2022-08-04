package io.prizy.domain.game.service;


import io.prizy.domain.game.model.GameBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal Dogga
 * @created 18/07/2022 17:03
 */

class GameBoardRandomizerTest {

  private final GameBoardRandomizer randomizer = new GameBoardRandomizer();
  private final GameBoard board = GameBoard.builder()
    .rowSize(4)
    .cells(new Integer[]{
      1, 1, 1, 1,
      1, 1, 1, 1,
      1, 1, 1, 1,
      1, 1, 1, 1
    })
    .build();

  @RepeatedTest(100)
  @DisplayName("Should generate random start end points with minimum distance of 1")
  void shouldRespectMinimumDistance() {
    var points = randomizer.randomStartEndPosition(board);

    var start = points.getLeft();
    var end = points.getRight();

    var xs = start % board.rowSize();
    var ys = Math.floorDiv(start, board.rowSize());

    var xe = end % board.rowSize();
    var ye = Math.floorDiv(end, board.rowSize());

    var dx = Math.abs(xs - xe);
    var dy = Math.abs(ys - ye);

    assertThat(dx > 1 || dy > 1).isTrue();
  }

}