package io.prizy.domain.contest.command;

import java.time.Instant;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:58 AM
 */

public sealed interface CreatePack {

  String name();

  Integer firstWinnerPosition();

  Integer lastWinnerPosition();

  @Builder
  record Product(
    String name,
    Integer firstWinnerPosition,
    Integer lastWinnerPosition,
    Integer quantity,
    Float value,
    String mediaId
  ) implements CreatePack {
  }

  @Builder
  record Coupon(
    String name,
    Integer firstWinnerPosition,
    Integer lastWinnerPosition,
    String code,
    Instant expiration
  ) implements CreatePack {
  }
}
