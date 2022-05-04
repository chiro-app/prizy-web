package io.prizy.domain.reward.event;

import io.prizy.domain.reward.domain.Reward;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 14:32
 */

public record RewardCreated(
  Reward reward
) {
}
