package io.prizy.domain.resources.model;

import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 14:42
 */

public sealed interface ResourceBalance {

  UUID userId();

  @Builder
  record ContestDependent(
    UUID userId,
    UUID contestId,
    Integer lives,
    Long diamonds
  ) implements ResourceBalance {
  }

  @Builder
  record Absolute(
    UUID userId,
    Integer keys
  ) implements ResourceBalance {
  }

}
