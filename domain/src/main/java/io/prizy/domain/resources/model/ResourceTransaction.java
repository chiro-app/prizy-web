package io.prizy.domain.resources.model;

import java.time.Instant;
import java.util.UUID;

public sealed interface ResourceTransaction {

  UUID id();
  Currency currency();
  TransactionType type();
  Long amount();
  UUID userId();
  Instant dateTime();

  record Absolute(
    UUID id,
    Currency currency,
    TransactionType type,
    Long amount,
    UUID userId,
    Instant dateTime
  ) implements ResourceTransaction {
  }

  record ContestDependent(
    UUID id,
    Currency currency,
    TransactionType type,
    Long amount,
    UUID userId,
    UUID contestId,
    Instant dateTime
  ) implements ResourceTransaction {
  }

}
