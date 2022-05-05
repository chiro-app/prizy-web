package io.prizy.domain.contest.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;

@Builder
public record ContestSubscription(
  UUID id,
  UUID userId,
  UUID contestId,
  Instant dateTime
) {
}
