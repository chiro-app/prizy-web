package io.prizy.adapters.resources.event.publisher;

import io.prizy.domain.resources.event.ResourceTransactionCreated;
import io.prizy.domain.resources.ports.ResourcePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 15:22
 */


@Component
@RequiredArgsConstructor
public class SpringResourcePublisher implements ResourcePublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(ResourceTransactionCreated event) {
    aep.publishEvent(event);
  }
}
