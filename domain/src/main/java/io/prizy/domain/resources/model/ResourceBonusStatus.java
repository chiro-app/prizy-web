package io.prizy.domain.resources.model;

import java.time.Instant;
import java.util.Optional;

import lombok.Builder;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 5/12/2022 1:24 AM
 */


@With
@Builder
public record ResourceBonusStatus(
  Boolean available,
  Optional<Instant> availableAt
) {
}
