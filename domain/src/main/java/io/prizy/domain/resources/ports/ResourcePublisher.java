package io.prizy.domain.resources.ports;

import java.util.Arrays;

import io.prizy.domain.resources.event.ResourceTransactionCreated;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 15:02
 */

public interface ResourcePublisher {
  void publish(ResourceTransactionCreated event);

  default void publish(ResourceTransactionCreated... events) {
    Arrays.stream(events).forEach(this::publish);
  }
}
