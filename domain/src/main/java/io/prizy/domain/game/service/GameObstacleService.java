package io.prizy.domain.game.service;

import io.prizy.domain.game.properties.GameProperties;
import io.prizy.domain.ranking.model.RankingRow;
import io.prizy.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 6/19/2022 4:14 PM
 */

@Service
@RequiredArgsConstructor
public class GameObstacleService {

  private final GameProperties properties;
  private final RankingService rankingService;

  public Integer obstacleCount(UUID userId, UUID contestId) {
    var userScore = rankingService
      .ofUser(userId, contestId)
      .map(RankingRow::score)
      .orElse(0L);
    return properties
      .obstacles()
      .stream()
      .filter(obstacle -> obstacle.from() <= userScore && userScore < obstacle.to())
      .findFirst()
      .map(GameProperties.ObstacleRange::obstacles)
      .get();
  }

}
