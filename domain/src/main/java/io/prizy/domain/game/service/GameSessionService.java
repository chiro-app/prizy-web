package io.prizy.domain.game.service;

import java.util.Collection;
import java.util.Random;
import java.util.stream.IntStream;

import io.prizy.domain.game.model.GameBoard;
import io.prizy.domain.game.model.GameSession;
import io.prizy.domain.game.properties.GameProperties;
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

  private static final Random RANDOM = new Random();

  private final GameProperties properties;

  public GameSession generate(GameBoard board) {
    var startEndPosition = randomStartEndPosition(board);
    var obstacles = randomObstacles(board);
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

  private Integer randomAvailablePosition(GameBoard board) {
    var availablePositions = IntStream
      .range(0, board.cells().length)
      .filter(index -> board.cells()[index] == 1)
      .toArray();
    return RANDOM.nextInt(availablePositions.length);
  }

  private Pair<Integer, Integer> randomStartEndPosition(GameBoard board) {
    return Pair.of(randomAvailablePosition(board), randomAvailablePosition(board));
  }

  public Collection<Integer> randomObstacles(GameBoard board) {
    var possibleObstacleCounts = IntStream
      .range(properties.minRandomObstacles(), properties.maxRandomObstacles())
      .toArray();
    var obstacleCount = possibleObstacleCounts[RANDOM.nextInt(possibleObstacleCounts.length)];
    return IntStream
      .range(0, obstacleCount)
      .mapToObj(i -> randomAvailablePosition(board))
      .distinct()
      .sorted()
      .toList();
  }

}
