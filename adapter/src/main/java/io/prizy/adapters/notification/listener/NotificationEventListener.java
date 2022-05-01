package io.prizy.adapters.notification.listener;

import io.prizy.domain.notification.event.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:48 AM
 */


@Slf4j
@Async
@Component
public class NotificationEventListener {

  @TransactionalEventListener
  public void onSendEmail(SendEmail event) {
    log.info("Sending email with subject {} to {}", event.email().subject(), event.email().userId());
  }

}
