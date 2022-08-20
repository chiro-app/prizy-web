package io.prizy;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.PushNotification;
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

  @Test
  @Sql("rankingintegrationtest/sql/ranking.sql")
  @DisplayName("Should notify deranking users when overtaken by first time players")
  void shouldNotifyDerankingUsersWhenOvertakenByFirstTimePlayers() {
    // Given
    stubOneSignal();

    var contestId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    var userId = UUID.fromString("00000000-0000-0000-0000-000000000005");

    // When
    sendEvent(RankingChanged.builder()
      .contestId(contestId)
      .userId(userId)
      .previousRank(Optional.empty())
      .build()
    );

    // Then
    assertThatEventCount(SendPushNotification.class, 1);
    assertThatEventEquals(SendPushNotification.class, 0,
      new SendPushNotification(PushNotification.MultipleUsers.builder()
        .userIds(Set.of(
          UUID.fromString("00000000-0000-0000-0000-000000000002"),
          UUID.fromString("00000000-0000-0000-0000-000000000000")
        ))
        .subject("Mince ! Tu viens de te faire dépasser !")
        .content("Reviens vite te positionner pour gagner ta récompense \uD83D\uDCAA")
        .build()
      ));
  }

  @Test
  @Sql("rankingintegrationtest/sql/ranking.sql")
  @DisplayName("Should notify deranking users when overtaken by already ranked players")
  void shouldNotifyDerankingUsersWhenOvertakenByRankedPlayers() {
    // Given
    stubOneSignal();

    var contestId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    var userId = UUID.fromString("00000000-0000-0000-0000-000000000005");

    // When
    sendEvent(RankingChanged.builder()
      .contestId(contestId)
      .userId(userId)
      .previousRank(Optional.of(2))
      .build()
    );

    // Then
    assertThatEventCount(SendPushNotification.class, 1);
    assertThatEventEquals(SendPushNotification.class, 0,
      new SendPushNotification(PushNotification.MultipleUsers.builder()
        .userIds(Set.of(UUID.fromString("00000000-0000-0000-0000-000000000000")))
        .subject("Mince ! Tu viens de te faire dépasser !")
        .content("Reviens vite te positionner pour gagner ta récompense \uD83D\uDCAA")
        .build()
      ));
  }

}
