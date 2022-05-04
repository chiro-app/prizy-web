package io.prizy.domain.reward.domain;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 7:12 PM
 */


@Builder
public record Reward(
  UUID id,
  UUID userId,
  UUID packId,
  Instant created
) {
}
