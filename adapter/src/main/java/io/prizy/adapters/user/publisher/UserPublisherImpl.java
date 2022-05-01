package io.prizy.adapters.user.publisher;

import io.prizy.domain.user.event.UserCreated;
import io.prizy.domain.user.event.UserUpdated;
import io.prizy.domain.user.port.UserPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 12:36 PM
 */


@Component
@RequiredArgsConstructor
public class UserPublisherImpl implements UserPublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(UserCreated event) {
    aep.publishEvent(event);
  }

  @Override
  public void publish(UserUpdated event) {
    aep.publishEvent(event);
  }
}
