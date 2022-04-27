package io.prizy.domain.contest.model;

import java.time.Instant;
import java.util.UUID;

public record ContestSubscription(
  UUID id,
  UUID userId,
  UUID contestId,
  Instant dateTime
) {
}
