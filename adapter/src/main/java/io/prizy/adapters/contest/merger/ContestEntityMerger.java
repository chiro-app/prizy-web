package io.prizy.adapters.contest.merger;

import java.util.Objects;

import io.prizy.adapters.contest.persistence.entity.ContestEntity;
import io.prizy.domain.contest.model.Contest;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/4/2022 10:01 PM
 */


@UtilityClass
public class ContestEntityMerger {

  public ContestEntity merge(ContestEntity entity, Contest contest) {
    // Reuse non-updatable columns
    return entity.toBuilder()
      .accessCode(Objects.requireNonNullElse(contest.accessCode(), entity.getAccessCode()))
      .build();
  }
}
