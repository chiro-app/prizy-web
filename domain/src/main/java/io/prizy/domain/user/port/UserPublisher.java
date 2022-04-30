package io.prizy.domain.user.port;

import io.prizy.domain.user.event.UserCreated;
import io.prizy.domain.user.event.UserUpdated;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:21 PM
 */


public interface UserPublisher {
  void publish(UserCreated event);

  void publish(UserUpdated event);
}
