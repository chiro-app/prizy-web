package io.prizy;

import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.ranking.event.RankingChanged;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 1:32 PM
 */


@DisplayName("Ranking")
public class RankingIntegrationTest extends IntegrationTest {

  private static final UUID CONTEST_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
  private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000005");

  @Test
  @Sql("rankingintegrationtest/sql/ranking.sql")
  @DisplayName("Should notify deranking users when overtaken by first time players")
  void shouldNotifyDerankingUsersWhenOvertakenByFirstTimePlayers() {
    // Given
    stubOneSignal();

    // When
    publishEvent(RankingChanged.builder().contestId(CONTEST_ID).userId(USER_ID).previousRank(Optional.empty()).build());

    // Then
    assertRequestedWithBody("/", resourceFile("onesignal/deranking-push-notification-multi-users.json"));
  }

  @Test
  @Sql("rankingintegrationtest/sql/ranking.sql")
  @DisplayName("Should notify deranking users when overtaken by already ranked players")
  void shouldNotifyDerankingUsersWhenOvertakenByRankedPlayers() {
    // Given
    stubOneSignal();

    // When
    publishEvent(RankingChanged.builder().contestId(CONTEST_ID).userId(USER_ID).previousRank(Optional.of(2)).build());

    // Then
    assertRequestedWithBody("/", resourceFile("onesignal/deranking-push-notification-single-user.json"));
  }

}
