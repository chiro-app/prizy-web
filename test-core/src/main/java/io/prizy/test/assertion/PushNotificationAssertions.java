package io.prizy.test.assertion;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
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

  default OneSignalPushNotificationAssertion assertThatPushNotification() {
    return new OneSignalPushNotificationAssertion(MOCK_SERVER_EXTENSION.retrieveRequests(ONESIGNAL_PATH));
  }

  enum OneSignalLocale {
    @JsonProperty("fr")
    FRENCH,
    @JsonProperty("en")
    ENGLISH
  }

  @Slf4j
  class OneSignalPushNotificationAssertion extends MockServerAssertions.MockServerRequestAssertion {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Collection<CreateOneSignalNotification> notifications;

    public OneSignalPushNotificationAssertion(Collection<HttpRequest> recordedRequests) {
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

    public OneSignalPushNotificationAssertion hasSubject(String subject) {
      assertThat(notifications)
        .anyMatch(notification -> subject.equals(notification.headings.get(OneSignalLocale.FRENCH))
          || subject.equals(notification.headings.get(OneSignalLocale.ENGLISH)));
      return this;
    }

    public OneSignalPushNotificationAssertion hasContent(String content) {
      assertThat(notifications)
        .anyMatch(notification -> content.equals(notification.contents.get(OneSignalLocale.FRENCH))
          || content.equals(notification.contents.get(OneSignalLocale.ENGLISH)));
      return this;
    }

    public OneSignalPushNotificationAssertion hasRecipients(Collection<UUID> recipients) {
      assertThat(notifications)
        .anyMatch(notification -> recipients.equals(notification.userIds));
      return this;
    }

    public OneSignalPushNotificationAssertion withVariables(Map<String, Object> variables) {
      assertThat(notifications)
        .anyMatch(notification -> notification.variables
          .map(variables::equals)
          .orElse(true)
        );
      return this;
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
