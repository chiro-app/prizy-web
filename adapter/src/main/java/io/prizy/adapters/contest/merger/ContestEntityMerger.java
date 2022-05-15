package io.prizy.adapters.contest.merger;

import java.util.Objects;

import io.prizy.adapters.contest.persistence.entity.ContestEntity;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/4/2022 10:01 PM
 */


@UtilityClass
public class ContestEntityMerger {

  public ContestEntity merge(ContestEntity entity, ContestEntity delta) {
    // Reuse non-updatable columns
    return entity.toBuilder()
      .accessCode(Objects.requireNonNullElse(delta.getAccessCode(), entity.getAccessCode()))
      .build();
  }
}
