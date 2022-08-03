package io.prizy.adapters.reward.publisher;

import io.prizy.domain.reward.event.RewardCreated;
import io.prizy.domain.reward.port.RewardPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 15:05
 */

@Component
@RequiredArgsConstructor
public class SpringRewardPublisher implements RewardPublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(RewardCreated event) {
    aep.publishEvent(event);
  }

}
