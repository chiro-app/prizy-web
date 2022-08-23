package io.prizy.adapters.notification.listener;

import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:48 AM
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

  private final NotificationService notificationService;

  @Async
  @EventListener
  public void onSendEmail(SendEmail event) {
    log.info("Sending email with subject {} to {}", event.email().subject(), event.email().userId());
    notificationService.send(event.email());
  }

  @Async
  @EventListener
  public void onSendPushNotification(SendPushNotification event) {
    log.info("Sending push notification {}", event.push());
    notificationService.send(event.push());
  }

}
