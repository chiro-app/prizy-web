package io.prizy.domain.contest.command;

import java.time.Instant;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:58 AM
 */

public sealed interface CreatePack {

  String name();
  Integer firstWinnerPosition();
  Integer lastWinnerPosition();

  record Product(
    String name,
    Integer firstWinnerPosition,
    Integer lastWinnerPosition,
    Integer quantity,
    Float value,
    String mediaId
  ) implements CreatePack {}

  record Coupon(
    String name,
    Integer firstWinnerPosition,
    Integer lastWinnerPosition,
    String code,
    Instant expiration
  ) implements CreatePack {}
}
