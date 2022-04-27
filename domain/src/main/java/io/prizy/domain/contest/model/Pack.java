package io.prizy.domain.contest.model;

import java.time.Instant;
import java.util.UUID;

public sealed interface Pack {

  UUID id();

  String name();

  Integer lastWinnerPosition();

  Integer firstWinnerPosition();

  record Product(
    UUID id,
    String name,
    Integer lastWinnerPosition,
    Integer firstWinnerPosition,
    Integer quantity,
    Float value,
    String mediaId
  ) implements Pack {
  }

  record Coupon(
    UUID id,
    String name,
    Integer lastWinnerPosition,
    Integer firstWinnerPosition,
    String code,
    Instant expiration
  ) implements Pack {
  }
}



