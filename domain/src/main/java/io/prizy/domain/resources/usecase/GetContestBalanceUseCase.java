package io.prizy.domain.resources.usecase;

import java.util.UUID;

import io.prizy.domain.resources.model.ResourceBalance;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 10:17
 */


@Component
@RequiredArgsConstructor
public class GetContestBalanceUseCase {

  private final ResourceRepository resourceRepository;

  public ResourceBalance.ContestDependent get(UUID userId, UUID contestId) {
    return ResourceBalance.ContestDependent.builder()
      .userId(userId)
      .contestId(contestId)
      .diamonds(resourceRepository.countDiamonds(userId, contestId))
      .lives(resourceRepository.countLives(userId, contestId))
      .build();
  }

}
