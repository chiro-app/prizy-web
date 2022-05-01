package io.prizy.domain.auth.model;

import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:30 AM
 */


@Builder
public record ResetToken(
  String token,
  UUID userId
) {
}
