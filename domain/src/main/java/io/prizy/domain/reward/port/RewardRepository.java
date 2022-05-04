package io.prizy.domain.reward.port;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.reward.domain.Reward;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 13:56
 */

public interface RewardRepository {
  Collection<Reward> save(Collection<Reward> rewards);

  Optional<Reward> lastCreated();

  Collection<Reward> byUserId(UUID userId);
}
