package io.prizy.domain.notification.publisher;

import io.prizy.domain.notification.event.SendEmail;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:18 AM
 */


public interface NotificationPublisher {
  void publish(SendEmail event);
}
