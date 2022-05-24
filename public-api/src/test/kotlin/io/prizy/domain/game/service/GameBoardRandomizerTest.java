package io.prizy.domain.game.service;

import java.util.Objects;
import java.util.UUID;

import io.prizy.domain.game.model.GameBoard;
import io.prizy.domain.game.properties.GameProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Nidhal Dogga
 * @created 5/24/2022 10:05 PM
 */


class GameBoardRandomizerTest {

  private final GameProperties properties = new GameProperties(6, 3);
  private final GameBoardRandomizer gameBoardRandomizer = new GameBoardRandomizer(properties);
  private final GameBoard gameBoard = new GameBoard(UUID.randomUUID(), "test", new Integer[]{1, 1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 6);

  @RepeatedTest(1000)
  public void shouldGenerateCorrectStartEndPositions() {
    var startEndPositions = gameBoardRandomizer.randomStartEndPosition(gameBoard);
    assertNotEquals(startEndPositions.getLeft(), startEndPositions.getRight());
    assertEquals(1, gameBoard.cells()[startEndPositions.getLeft()]);
    assertEquals(1, gameBoard.cells()[startEndPositions.getRight()]);
  }

  @RepeatedTest(1000)
  public void shouldGenerateCorrectRandomPositions() {
    var first = gameBoardRandomizer.randomAvailablePosition(gameBoard);
    var second = gameBoardRandomizer.randomAvailablePosition(gameBoard, first);
    var third = gameBoardRandomizer.randomAvailablePosition(gameBoard, first, second);
    assertNotEquals(first, second);
    assertNotEquals(second, third);
    assertNotEquals(first, third);
  }

  @RepeatedTest(1000)
  public void shouldGenerateCorrectObstacles() {
    var startEnd = gameBoardRandomizer.randomStartEndPosition(gameBoard);
    var obstacles = gameBoardRandomizer.randomObstacles(gameBoard, startEnd.getLeft(), startEnd.getRight());
    assertTrue(obstacles.size() <= properties.maxRandomObstacles());
    assertTrue(obstacles.size() >= properties.minRandomObstacles());
    assertTrue(obstacles.stream().allMatch(i -> gameBoard.cells()[i] == 1));
    assertTrue(obstacles.stream().noneMatch(i -> Objects.equals(i, startEnd.getLeft())));
    assertTrue(obstacles.stream().noneMatch(i -> Objects.equals(i, startEnd.getRight())));
    assertEquals(obstacles.stream().distinct().count(), obstacles.size());
  }

}