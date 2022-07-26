package io.prizy.domain.notification.model;

import java.util.Map;
import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:09 AM
 */


public sealed interface PushNotification {

  String subject();

  String content();

  Map<String, Object> data();


  @Builder
  record SingleUser(
    UUID userId,
    String subject,
    String content,
    Map<String, Object> data
  ) implements PushNotification {

  }

  @Builder
  record AllUsers(
    String subject,
    String content,
    Map<String, Object> data
  ) implements PushNotification {

  }

}
