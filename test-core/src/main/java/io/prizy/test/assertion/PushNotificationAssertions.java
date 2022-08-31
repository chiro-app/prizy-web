package io.prizy.test.assertion;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.MediaType;

import static io.prizy.test.assertion.MockServerAssertions.MOCK_SERVER_EXTENSION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author Nidhal Dogga
 * @created 8/28/2022 11:08 AM
 */

public interface PushNotificationAssertions {

  String ONESIGNAL_PATH = "/onesignal";

  default void stubOneSignal() {
    MOCK_SERVER_EXTENSION.stubAllRequests(ONESIGNAL_PATH, response()
      .withStatusCode(200)
      .withContentType(MediaType.APPLICATION_JSON)
      // Sample OK response
      .withBody("""
        {
          "id": "00000000-0000-0000-0000-000000000000",
          "recipients": 1,
          "external_id": "00000000-0000-0000-0000-000000000000"
        }
        """)
    );
  }

  default OneSignalMultiplePushNotificationsAssertion assertThatPushNotification() {
    return new OneSignalMultiplePushNotificationsAssertion(MOCK_SERVER_EXTENSION.retrieveRequests(ONESIGNAL_PATH));
  }

  enum OneSignalLocale {
    @JsonProperty("fr")
    FRENCH,
    @JsonProperty("en")
    ENGLISH
  }


  @RequiredArgsConstructor
  class OneSignalSinglePushNotificationAssertion {

    private final CreateOneSignalNotification notification;

    public OneSignalSinglePushNotificationAssertion hasSubject(String subject) {
      assertThat(subject).isIn(notification.headings.get(OneSignalLocale.FRENCH),
        notification.headings.get(OneSignalLocale.ENGLISH));
      return this;
    }

    public OneSignalSinglePushNotificationAssertion hasContent(String content) {
      assertThat(content).isIn(notification.contents.get(OneSignalLocale.FRENCH),
        notification.contents.get(OneSignalLocale.ENGLISH));
      return this;
    }

    public OneSignalSinglePushNotificationAssertion hasRecipients(Collection<UUID> recipients) {
      assertThat(Set.of(recipients)).isEqualTo(Set.of(notification.userIds));
      return this;
    }

    public OneSignalSinglePushNotificationAssertion withVariables(Map<String, Object> variables) {
      assertThat(variables).isEqualTo(notification.variables.orElse(Map.of()));
      return this;
    }

  }

  @Slf4j
  class OneSignalMultiplePushNotificationsAssertion extends MockServerAssertions.MockServerRequestAssertion {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final List<CreateOneSignalNotification> notifications;

    public OneSignalMultiplePushNotificationsAssertion(Collection<HttpRequest> recordedRequests) {
      super(recordedRequests);
      notifications = recordedRequests.stream()
        .map(request -> {
          try {
            return OBJECT_MAPPER.readValue(new String(request.getBodyAsRawBytes(), StandardCharsets.UTF_8),
              CreateOneSignalNotification.class);
          } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
          }
          return null;
        })
        .toList();
    }

    public OneSignalMultiplePushNotificationsAssertion hasNumberOfNotifications(int size) {
      assertThat(notifications).hasSize(size);
      return this;
    }

    public void isEmpty() {
      assertThat(notifications).isEmpty();
    }

    public OneSignalSinglePushNotificationAssertion atIndex(int index) {
      return new OneSignalSinglePushNotificationAssertion(notifications.get(index));
    }

  }

  @Builder
  record CreateOneSignalNotification(
    @JsonProperty("app_id")
    String appId,
    Map<OneSignalLocale, String> contents,
    Map<OneSignalLocale, String> headings,
    @JsonProperty("included_segments")
    Collection<String> segments,
    @JsonProperty("data")
    Optional<Map<String, Object>> variables,
    @JsonProperty("android_accent_color")
    String androidAccentColor,
    @JsonProperty("include_external_user_ids")
    Collection<UUID> userIds,
    @JsonProperty("channel_for_external_user_ids")
    String channel,
    @JsonProperty("large_icon")
    String androidLargeIcon
  ) {
  }

}
