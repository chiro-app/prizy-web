package io.prizy.domain.resources.usecase;

import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.resources.model.ResourceBonusStatus;
import io.prizy.domain.resources.model.ResourceTransaction;
import io.prizy.domain.resources.ports.ResourceRepository;
import io.prizy.domain.resources.service.ResourceBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 09:49
 */


@Component
@RequiredArgsConstructor
public class GetContestBonusStatusUseCase {

  private final ResourceRepository resourceRepository;
  private final ResourceBonusService resourceBonusService;

  public ResourceBonusStatus get(UUID userId, UUID contestId) {
    return resourceBonusService
      .getAvailableContestBonus(userId, contestId)
      .map(bonus -> ResourceBonusStatus.builder()
        .available(true)
        .availableAt(Optional.empty())
        .build()
      )
      .orElseGet(() -> ResourceBonusStatus.builder()
        .available(false)
        .availableAt(resourceRepository
          .lastTransaction(userId)
          .map(ResourceTransaction::dateTime)
          .map(dateTime -> dateTime.plus(1, DAYS))
        )
        .build()
      );
  }

}
