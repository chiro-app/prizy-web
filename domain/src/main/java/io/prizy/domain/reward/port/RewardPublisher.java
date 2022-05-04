package io.prizy.domain.reward.port;

import io.prizy.domain.reward.event.RewardCreated;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 14:32
 */

public interface RewardPublisher {
  void publish(RewardCreated event);
}
