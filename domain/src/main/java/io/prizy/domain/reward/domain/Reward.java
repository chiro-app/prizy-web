package io.prizy.domain.reward.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 7:12 PM
 */


public record Reward(
  UUID id,
  UUID userId,
  UUID packId,
  Instant created
) {
}
