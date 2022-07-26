package io.prizy.adapters.notification.properties;

/**
 * @author Nidhal Dogga
 * @created 26/07/2022 11:07
 */

public record PushNotificationProperties(
  String apiUrl,
  String apiKey,
  String appId
) {
}
