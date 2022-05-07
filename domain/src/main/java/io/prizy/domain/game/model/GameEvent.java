package io.prizy.domain.game.model;

import java.util.Collection;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Nidhal Dogga
 * @created 5/6/2022 9:35 PM
 */


public sealed interface GameEvent {

  default Boolean isFinal() {
    return false;
  }

  record Error(Code errorCode) implements GameEvent {

    @Override
    public Boolean isFinal() {
      return true;
    }

    public enum Code {
      ILLEGAL_EVENT,
      UNRECOGNIZED_EVENT,
      CONTEST_NOT_FOUND,
      CONTEST_ENDED,
      CONTEST_NOT_STARTED,
      USER_NOT_SUBSCRIBED,
      INVALID_CREDENTIALS,
      GAME_NOT_STARTED,
      GANE_ALREADY_STARTED,
      INSUFFICIENT_RESOURCES,
      GAME_ATTEMPTS_EXCEEDED,
      ILLEGAL_MOVE,
      UNKNOWN,
    }
  }

  record GameLost(Collection<Integer> obstacles) implements GameEvent {

    @Override
    public Boolean isFinal() {
      return true;
    }

  }

  record GameWon(Collection<Integer> obstacles, Long score) implements GameEvent {

    @Override
    public Boolean isFinal() {
      return true;
    }
  }

  record GameStarted(UUID contestId, Long diamonds) implements GameEvent {
  }

  record PlayerMoved(Direction direction) implements GameEvent {

    @RequiredArgsConstructor
    public enum Direction {

      UP(0, -1),
      DOWN(0, 1),
      LEFT(-1, 0),
      RIGHT(1, 0),
      UP_LEFT(-1, -1),
      UP_RIGHT(1, -1),
      DOWN_LEFT(-1, 1),
      DOWN_RIGHT(1, 1);

      private final Integer dx, dy;

      public Pair<Integer, Integer> getDelta() {
        return Pair.of(dx, dy);
      }

    }

  }

  record BoardRetrieved(
    GameBoard board,
    Integer startPosition,
    Integer endPosition,
    Collection<Integer> obstacles
  ) implements GameEvent {
  }

  record ScoreUpdated(Long score) implements GameEvent {
  }

}
