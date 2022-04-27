package io.prizy.adapters.contest.event.publisher;

import io.prizy.domain.contest.event.ContestCreated;
import io.prizy.domain.contest.event.ContestUpdated;
import io.prizy.domain.contest.ports.ContestPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 4:04 PM
 */

@Component
@RequiredArgsConstructor
public class SpringContestPublisher implements ContestPublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(ContestCreated event) {
    aep.publishEvent(event);
  }

  @Override
  public void publish(ContestUpdated event) {
    aep.publishEvent(event);
  }
}
