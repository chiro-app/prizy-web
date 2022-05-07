package io.prizy.domain.game.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.game.model.GameContext;
import io.prizy.domain.game.model.GameEvent;
import io.prizy.domain.game.model.GameEvent.Error.Code;
import io.prizy.domain.game.model.GameEvent.GameLost;
import io.prizy.domain.game.model.GameEvent.GameWon;
import io.prizy.domain.game.port.GameBoardRepository;
import io.prizy.domain.resources.service.ResourceService;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 4:21 PM
 */


@Service
@Transactional
@RequiredArgsConstructor
public class GameEngine {

  private final UserRepository userRepository;
  private final ResourceService resourceService;
  private final ContestRepository contestRepository;
  private final GameBoardRepository gameBoardRepository;
  private final GameSessionService sessionService;

  // TODO(Nidhal): Replace with a reactor context solution
  private final AtomicReference<Map<String, GameContext>> activeContexts = new AtomicReference<>(new HashMap<>());

  public Mono<GameEvent> startGame(String requestId, UUID userId, GameEvent.GameStarted event) {
    if (activeContexts.get().containsKey(requestId)) {
      return Mono.just(new GameEvent.Error(Code.GANE_ALREADY_STARTED));
    }

    var user = userRepository.byId(userId).get();
    var maybeContest = contestRepository.byId(event.contestId());
    if (maybeContest.isEmpty()) {
      return Mono.just(new GameEvent.Error(Code.CONTEST_NOT_FOUND));
    }
    var contest = maybeContest.get();

    var balance = resourceService.getContestDependentBalance(userId, contest.id());
    if (balance.diamonds() < event.diamonds()) {
      return Mono.just(new GameEvent.Error(Code.INSUFFICIENT_RESOURCES));
    }
    if (balance.lives() < 1) {
      return Mono.just(new GameEvent.Error(Code.INSUFFICIENT_RESOURCES));
    }

    var board = gameBoardRepository.byId(contest.boardId()).get();
    var session = sessionService.generate(board);

    resourceService.debitLives(user.id(), contest.id(), 1);
    resourceService.debitDiamonds(user.id(), contest.id(), event.diamonds());

    return Mono.fromCallable(() -> {
      var gameContext = new GameContext(user, contest, session, event.diamonds());
      activeContexts.get().put(requestId, gameContext);
      return new GameEvent.BoardRetrieved(
        board,
        session.startPosition(),
        session.endPosition(),
        session.obstacles()
      );
    });
  }

  public Mono<GameEvent> movePlayer(String requestId, UUID userId, GameEvent.PlayerMoved event) {
    return Mono.fromCallable(() -> {
      Optional<GameContext> maybeContext = Optional.ofNullable(activeContexts.get().get(requestId));

      if (maybeContext.isEmpty()) {
        return new GameEvent.Error(Code.GAME_NOT_STARTED);
      }

      var context = maybeContext.get();
      var session = context.session();
      var user = context.user();
      var contest = context.contest();
      var initialDiamondsBet = context.initialDiamondsBet();

      var delta = event.direction().getDelta();
      var currentPosition = session.currentPosition();
      var newPosition = sessionService.applyDelta(currentPosition, delta, session.board());

      if (!sessionService.isValidPosition(newPosition, session.board())) {
        return new GameEvent.Error(Code.ILLEGAL_MOVE);
      }
      if (session.obstacles().contains(newPosition)) {
        return new GameLost(session.obstacles());
      }
      if (session.endPosition().equals(newPosition)) {
        resourceService.creditDiamonds(user.id(), contest.id(), session.score());
        return new GameWon(session.obstacles(), session.score());
      }

      var newScore = initialDiamondsBet + session.score();
      var newGameContext = context
        .withSession(session
          .withCurrentPosition(newPosition)
          .withScore(newScore)
        );

      activeContexts.get().put(requestId, newGameContext);

      return new GameEvent.ScoreUpdated(newScore);
    });
  }

  public void endGame(String requestId) {
    activeContexts.get().remove(requestId);
  }

}
