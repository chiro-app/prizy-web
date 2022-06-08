package io.prizy.domain.resources.usecase;

import java.util.UUID;

import io.prizy.domain.resources.model.ResourceBoost;
import io.prizy.domain.resources.service.ResourceBoostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 10:08
 */


@Component
@RequiredArgsConstructor
public class GetContestBoostUseCase {

  private final ResourceBoostService boostService;

  public ResourceBoost get(UUID userId, UUID contestId) {
    return boostService.boost(userId, contestId);
  }

}
