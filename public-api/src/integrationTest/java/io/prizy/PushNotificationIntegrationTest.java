package io.prizy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.prizy.adapters.resources.schduler.DailyResourceNotificationScheduler;
import io.prizy.domain.ranking.event.RankingChanged;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 1:32 PM
 */


@DisplayName("Push notifications")
public class PushNotificationIntegrationTest extends IntegrationTest {

  private static final UUID CONTEST_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
  private static final UUID USER_ID_00 = UUID.fromString("00000000-0000-0000-0000-000000000000");
  private static final UUID USER_ID_01 = UUID.fromString("00000000-0000-0000-0000-000000000001");
  private static final UUID USER_ID_03 = UUID.fromString("00000000-0000-0000-0000-000000000003");
  private static final UUID USER_ID_06 = UUID.fromString("00000000-0000-0000-0000-000000000006");
  private static final UUID USER_ID_0A = UUID.fromString("00000000-0000-0000-0000-00000000000a");

  @Test
//  @Disabled
  @Sql("pushnotificationintegrationtest/sql/ranking_push_notification.sql")
  @DisplayName("Should notify deranking users when overtaken by first time players")
  void shouldNotifyDerankingUsersWhenOvertakenByFirstTimePlayers() {
    // Given
    stubOneSignal();

    // When
    publishEvent(RankingChanged.builder()
      .contestId(CONTEST_ID)
      .userId(USER_ID_01)
      .previousRank(Optional.empty())
      .build()
    );

    // Then
    Awaitility
      .await()
      .atMost(5, TimeUnit.SECONDS)
      .untilAsserted(() -> assertThatPushNotification()
        .hasNumberOfNotifications(1)
        .atIndex(0)
        .hasContent("Reviens vite te positionner pour gagner ta récompense \uD83D\uDCAA")
        .hasSubject("Mince ! Tu viens de te faire dépasser !")
        .hasRecipients(List.of(USER_ID_03, USER_ID_06, USER_ID_0A))
      );
  }

  @Test
//  @Disabled
  @Sql("pushnotificationintegrationtest/sql/ranking_push_notification.sql")
  @DisplayName("Should notify deranking users when overtaken by already ranked players")
  void shouldNotifyDerankingUsersWhenOvertakenByRankedPlayers() {
    // Given
    stubOneSignal();

    // When
    publishEvent(RankingChanged.builder()
      .contestId(CONTEST_ID)
      .userId(USER_ID_01)
      .previousRank(Optional.of(8))
      .build()
    );

    // Then
    Awaitility
      .await()
      .atMost(5, TimeUnit.SECONDS)
      .untilAsserted(() -> assertThatPushNotification()
        .hasNumberOfNotifications(1)
        .atIndex(0)
        .hasContent("Reviens vite te positionner pour gagner ta récompense \uD83D\uDCAA")
        .hasSubject("Mince ! Tu viens de te faire dépasser !")
        .hasRecipients(List.of(USER_ID_03, USER_ID_06))
      );
  }

  @Test
  @Sql("pushnotificationintegrationtest/sql/ranking_push_notification.sql")
  @DisplayName("Should not send any notifications when nobody has deranked")
  void shouldNotSendAnyNotificationWhenNobodyHasDeranked() throws InterruptedException {
    // Given
    stubOneSignal();

    // When
    publishEvent(RankingChanged.builder()
      .contestId(CONTEST_ID)
      .userId(USER_ID_01)
      .previousRank(Optional.of(2))
      .build()
    );

    // Then
    Thread.sleep(5000);
    assertThatPushNotification().isEmpty();
  }

  @Test
  @Sql("pushnotificationintegrationtest/sql/keys_bonus_push_notification.sql")
  @DisplayName("Should send keys bonus push notification")
  void shouldSendKeysBonusPushNotification() {
    // Given
    stubOneSignal();

    // When
    invokeScheduledTask(DailyResourceNotificationScheduler.class, "notifyForDailyResourceBonus");

    // Then
    Awaitility
      .await()
      .atMost(5, TimeUnit.SECONDS)
      .untilAsserted(() -> assertThatPushNotification()
        .hasNumberOfNotifications(2)
        .atIndex(1)
        .hasSubject("Tes clés sont arrivées ! \uD83D\uDE0A")
        .hasContent("Viens vite les récupérer pour participer aux nouveaux concours")
        .hasRecipients(List.of(USER_ID_00))
      );
  }

}
