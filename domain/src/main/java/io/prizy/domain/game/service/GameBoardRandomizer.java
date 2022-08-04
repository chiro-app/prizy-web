package io.prizy.domain.game.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.prizy.domain.game.model.GameBoard;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GameBoardRandomizer {

  private static final Random RANDOM = ThreadLocalRandom.current();

  public Integer randomAvailablePosition(GameBoard board, Integer... excluded) {
    var availablePositions = IntStream
      .range(0, board.cells().length)
      .filter(index -> board.cells()[index] == 1 && Arrays.stream(excluded).noneMatch(i -> i == index))
      .toArray();
    return availablePositions[RANDOM.nextInt(availablePositions.length - 1)];
  }

  public Pair<Integer, Integer> randomStartEndPosition(GameBoard board) {
    var startPosition = randomAvailablePosition(board);
    var excludedPositions = new ArrayList<>(neighbouringPoints(startPosition, board));
    excludedPositions.add(startPosition);
    var endPosition = randomAvailablePosition(board, excludedPositions.toArray(new Integer[]{}));
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

  private List<Integer> neighbouringPoints(Integer point, GameBoard board) {
    return IntStream.range(0, board.cells().length)
      .filter(index -> {
        if (index == point) {
          return false;
        }

        var x1 = index % board.rowSize();
        var y1 = Math.floorDiv(index, board.rowSize());

        var x2 = point % board.rowSize();
        var y2 = Math.floorDiv(point, board.rowSize());

        var dx = Math.abs(x1 - x2);
        var dy = Math.abs(y1 - y2);

        return dx <= 1 && dy <= 1;
      })
      .boxed()
      .toList();
  }

}
