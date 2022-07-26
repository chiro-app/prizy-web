package io.prizy.domain.notification.port;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 10:15 PM
 */


public interface PushNotificationContentCompiler {
  String compile(String content, Object data);
}
