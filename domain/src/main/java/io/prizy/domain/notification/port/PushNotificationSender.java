package io.prizy.domain.notification.port;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 10:07 PM
 */


public interface PushNotificationSender {
  void send(UUID userId, String subject, String content);

  void sendToAllUsers(String subject, String content);

  void sendToMultipleUsers(Collection<UUID> userIds, String subject, String content);
}
