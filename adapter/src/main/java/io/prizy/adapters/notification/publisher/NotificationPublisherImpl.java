package io.prizy.adapters.notification.publisher;

import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:47 AM
 */


@Component
@RequiredArgsConstructor
public class NotificationPublisherImpl implements NotificationPublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(SendEmail event) {
    aep.publishEvent(event);
  }

}
