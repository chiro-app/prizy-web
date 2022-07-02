package io.prizy.adapters.notification.listener;

import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

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
  @TransactionalEventListener
  public void onSendEmail(SendEmail event) {
    log.info("Sending email with subject {} to {}", event.email().subject(), event.email().userId());
    notificationService.send(event.email());
  }

}
