package io.prizy.domain.notification.port;

import io.prizy.domain.user.model.User;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 10:07 PM
 */


public interface PushNotificationSender {
  void send(User user, String subject, String content);

  void sendToAllUsers(String subject, String content);
}
