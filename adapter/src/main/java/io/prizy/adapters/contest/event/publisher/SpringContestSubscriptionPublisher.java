package io.prizy.adapters.contest.event.publisher;

import io.prizy.domain.contest.event.ContestSubscriptionCreated;
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 4:05 PM
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringContestSubscriptionPublisher implements ContestSubscriptionPublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(ContestSubscriptionCreated event) {
    aep.publishEvent(event);
  }
}
