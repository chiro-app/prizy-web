package io.prizy.domain.resources.usecase;

import java.util.UUID;

import io.prizy.domain.contest.exception.UserNotSubscribedException;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import io.prizy.domain.resources.exception.ResourceBonusAlreadyClaimedException;
import io.prizy.domain.resources.properties.ResourceProperties;
import io.prizy.domain.resources.service.ResourceBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 09:32
 */


@Component
@RequiredArgsConstructor
public class ClaimContestBonusUseCase {

  private final ResourceBonusService bonusService;
  private final ContestSubscriptionRepository contestSubscriptionRepository;
  private final ResourceProperties resourceProperties;

  public void claim(UUID userId, UUID contestId) {
    if (!contestSubscriptionRepository.userSubscribedTo(userId, contestId)) {
      throw new UserNotSubscribedException(userId, contestId);
    }
    if (!bonusService.hasAvailableContestBonus(userId, contestId)) {
      throw new ResourceBonusAlreadyClaimedException();
    }
    bonusService.creditContestBonus(userId, contestId, resourceProperties.dailyLivesBonus(),
      resourceProperties.dailyDiamondsBonus());
  }

}
