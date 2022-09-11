package io.prizy.domain.contest.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.With;


public sealed interface Pack {

  UUID id();

  String name();

  Integer lastWinnerPosition();

  Integer firstWinnerPosition();

  @With
  @Builder
  record Product(
    UUID id,
    String name,
    Integer lastWinnerPosition,
    Integer firstWinnerPosition,
    Integer quantity,
    Double value,
    String assetId
  ) implements Pack {
  }

  @With
  @Builder
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



