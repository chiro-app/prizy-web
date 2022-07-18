package io.prizy.domain.game.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import io.prizy.domain.game.model.GameBoard;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Nidhal Dogga
 * @created 5/24/2022 10:03 PM
 */


@Component
public class GameBoardRandomizer {

  private static final Random RANDOM = ThreadLocalRandom.current();

  public Integer randomAvailablePosition(GameBoard board, Integer minimumMoves, Integer... excluded) {
    var availablePositions = IntStream
      .range(0, board.cells().length)
      .filter(index -> board.cells()[index] == 1 && Arrays.stream(excluded).noneMatch(i -> i == index))
      .filter(index -> Arrays
        .stream(excluded)
        .allMatch(excludedIndex -> {
          var dx = (index % board.rowSize()) - (excludedIndex % board.rowSize());
          var dy = (index / board.rowSize()) - (excludedIndex / board.rowSize());
          var distance = (Double) floor(sqrt(pow(dx, 2) + pow(dy, 2)));
          return distance.longValue() > minimumMoves;
        }))
      .toArray();
    return availablePositions[RANDOM.nextInt(availablePositions.length - 1)];
  }

  public Integer randomAvailablePosition(GameBoard board, Integer... excluded) {
    return randomAvailablePosition(board, 1, excluded);
  }

  public Integer randomAvailablePosition(GameBoard board, Integer excluded) {
    return randomAvailablePosition(board, new Integer[]{excluded});
  }

  public Pair<Integer, Integer> randomStartEndPosition(GameBoard board) {
    var startPosition = randomAvailablePosition(board);
    var endPosition = randomAvailablePosition(board, startPosition);
    return Pair.of(startPosition, endPosition);
  }

  public Collection<Integer> randomObstacles(GameBoard board, Integer start, Integer end, Integer obstacleCount) {
    var obstacles = new ArrayList<Integer>();
    for (int i = 0; i < obstacleCount; i++) {
      var excluded = new ArrayList<>(obstacles) {{
        add(start);
        add(end);
      }};
      var obstacle = randomAvailablePosition(board, excluded.toArray(Integer[]::new));
      obstacles.add(obstacle);
    }
    return obstacles;
  }

}
