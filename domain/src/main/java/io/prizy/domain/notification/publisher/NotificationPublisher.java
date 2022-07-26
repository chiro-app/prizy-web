package io.prizy.domain.notification.publisher;

import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.event.SendPushNotification;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:18 AM
 */


public interface NotificationPublisher {
  void publishEmail(SendEmail event);

  void publishPushNotification(SendPushNotification event);
}
