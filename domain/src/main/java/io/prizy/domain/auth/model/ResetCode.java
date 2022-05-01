package io.prizy.domain.auth.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:15 AM
 */


@Builder
public record ResetCode(
  String code,
  UUID userId,
  Instant created
) {
}
