package io.prizy.domain.game.model;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 5/6/2022 9:35 PM
 */


public sealed interface GameEvent {

  record Error(Code errorCode) implements GameEvent {

    public enum Code {
      UNRECOGNIZED_EVENT,
      CONTEST_NOT_FOUND,
      CONTEST_ENDED,
      CONTEST_NOT_STARTED,
      USER_NOT_SUBSCRIBED,
      INVALID_CREDENTIALS,
      GAME_NOT_STARTED,
      INSUFFICIENT_BET,
      GAME_ATTEMPTS_EXCEEDED,
      ILLEGAL_MOVE,
      GAME_NOT_INITIALIZED,
      UNKNOWN,
    }

  }

  record GameLost(Collection<Integer> obstacles) implements GameEvent {
  }

  record GameWon(Collection<Integer> obstacles, Long score) implements GameEvent {
  }

  record GameStarted(UUID contestId, Long diamonds) implements GameEvent {
  }

  record PlayerMoved(Direction direction) implements GameEvent {

    public enum Direction {
      UP,
      DOWN,
      LEFT,
      RIGHT
    }

  }

  record BoardRetrieved(GameBoard board, Integer startPosition, Integer endPosition) implements GameEvent {
  }

  record ScoreUpdated(Long score) implements GameEvent {
  }

}
