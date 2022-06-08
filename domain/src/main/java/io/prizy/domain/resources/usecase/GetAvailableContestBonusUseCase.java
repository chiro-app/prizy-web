package io.prizy.domain.resources.usecase;

import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.resources.model.ResourceBalance;
import io.prizy.domain.resources.service.ResourceBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 09:40
 */


@Component
@RequiredArgsConstructor
public class GetAvailableContestBonusUseCase {

  private final ResourceBonusService resourceBonusService;

  public Optional<ResourceBalance.ContestDependent> get(UUID userId, UUID contestId) {
    return resourceBonusService.getAvailableContestBonus(userId, contestId);
  }

}
