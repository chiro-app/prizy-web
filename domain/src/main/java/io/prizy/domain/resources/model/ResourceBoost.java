package io.prizy.domain.resources.model;

import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 15:16
 */


@Builder
public record ResourceBoost(
  UUID userId,
  UUID contestId,
  Integer lives,
  Long diamonds
) {
}
