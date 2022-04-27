package io.prizy.domain.resources.model;

import java.time.Instant;
import java.util.UUID;

public record ResourceTransaction(
  UUID id,
  ResourceType currency,
  ResourceTransactionType type,
  Integer amount,
  UUID userId,
  Instant dateTime
) {
}
