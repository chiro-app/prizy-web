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
  @DisplayName("Should generate random points with minimum distance to excluded ones")
  void shouldRespectMinimumDistance() {
    var point = randomizer.randomAvailablePosition(board, 2, new Integer[]{0});
    assertThat(point).isIn(3, 7, 11, 12, 13, 14, 15);
  }

}