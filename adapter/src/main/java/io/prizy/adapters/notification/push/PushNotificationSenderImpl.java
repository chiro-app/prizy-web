package io.prizy.adapters.notification.push;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.prizy.adapters.notification.properties.PushNotificationProperties;
import io.prizy.adapters.notification.push.model.CreateOneSignalNotification;
import io.prizy.adapters.notification.push.model.CreateOneSignalNotification.CreateOneSignalNotificationBuilder;
import io.prizy.adapters.notification.push.model.OneSignalResponse;
import io.prizy.adapters.notification.push.utils.OneSignalUtils;
import io.prizy.domain.notification.port.PushNotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 10:09 PM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationSenderImpl implements PushNotificationSender {

  private static final String SUBSCRIBED_USERS_SEGMENT = "Subscribed Users";

  private final PushNotificationProperties properties;

  private RestTemplate restTemplate;
  private ObjectMapper objectMapper;

  @Override
  public void send(UUID userId, String subject, String content) {
    sendPushNotification(subject, content, List.of(userId));
  }

  @Override
  public void sendToAllUsers(String subject, String content) {
    sendPushNotification(subject, content, SUBSCRIBED_USERS_SEGMENT);
  }

  @Override
  public void sendToMultipleUsers(Collection<UUID> userIds, String subject, String content) {
    sendPushNotification(subject, content, userIds);
  }

  @PostConstruct
  private void initWebClient() {
    objectMapper = Jackson2ObjectMapperBuilder.json()
      .serializationInclusion(NON_ABSENT)
      .build();
    restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add((request, body, execution) -> {
      var headers = request.getHeaders();
      if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
        headers.setBasicAuth(properties.apiKey());
      }
      return execution.execute(request, body);
    });
  }

  private void sendPushNotification(CreateOneSignalNotification request) {
    try {
      var requestJson = objectMapper.convertValue(request, Map.class);
      log.info("Sending notification through OneSignal {}", requestJson);
      var oneSignalResponse = restTemplate.postForObject(properties.apiUrl(), requestJson, OneSignalResponse.class);
      if (oneSignalResponse == null || !oneSignalResponse.isSuccessful()) {
        log.error("An error occurred while sending notification to OneSignal {}", oneSignalResponse);
        throw new IllegalStateException(oneSignalResponse.errors().toString());
      }
      log.info("Notification sent through OneSignal {}", oneSignalResponse);
    } catch (Exception exception) {
      log.error(exception.getMessage(), exception);
    }
  }

  private void sendPushNotification(String subject, String content, Collection<UUID> userIds) {
    sendPushNotification(subject, content, builder -> builder.userIds(userIds));
  }

  private void sendPushNotification(String subject, String content, String... segments) {
    sendPushNotification(subject, content, builder -> builder.segments(List.of(segments)));
  }

  private void sendPushNotification(String subject, String content,
                                    CreateOneSignalNotificationBuilderCustomizer customizer) {
    var builder = CreateOneSignalNotification.builder()
      .appId(properties.appId())
      .headings(OneSignalUtils.simpleLocalizedString(subject))
      .contents(OneSignalUtils.simpleLocalizedString(content));
    builder = customizer.apply(builder);
    var request = builder.build();
    sendPushNotification(request);
  }

  private interface CreateOneSignalNotificationBuilderCustomizer extends Function<CreateOneSignalNotificationBuilder,
    CreateOneSignalNotificationBuilder> {

  }
}
