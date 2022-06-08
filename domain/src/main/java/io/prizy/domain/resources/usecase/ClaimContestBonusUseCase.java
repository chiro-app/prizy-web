package io.prizy.domain.resources.usecase;

import java.util.UUID;

import io.prizy.domain.contest.exception.UserNotSubscribedException;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import io.prizy.domain.contest.service.ContestSubscriptionService;
import io.prizy.domain.resources.event.ResourceTransactionCreated;
import io.prizy.domain.resources.exception.ResourceBonusAlreadyClaimedException;
import io.prizy.domain.resources.ports.ResourcePublisher;
import io.prizy.domain.resources.ports.ResourceRepository;
import io.prizy.domain.resources.properties.ResourceProperties;
import io.prizy.domain.resources.service.ResourceBonusService;
import io.prizy.domain.resources.service.ResourceBoostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.prizy.domain.resources.model.Currency.DIAMONDS;
import static io.prizy.domain.resources.model.Currency.LIVES;
import static io.prizy.domain.resources.model.TransactionType.BONUS;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 09:32
 */


@Component
@RequiredArgsConstructor
public class ClaimContestBonusUseCase {

  private final ResourceBoostService boostService;
  private final ResourceBonusService bonusService;
  private final ContestSubscriptionService contestSubscriptionService;
  private final ResourceRepository resourceRepository;
  private final ContestSubscriptionRepository contestSubscriptionRepository;
  private final ResourcePublisher resourcePublisher;
  private final ResourceProperties resourceProperties;

  public void claim(UUID userId, UUID contestId) {
    if (!contestSubscriptionRepository.userSubscribedTo(userId, contestId)) {
      throw new UserNotSubscribedException(userId, contestId);
    }
    if (!bonusService.hasAvailableContestBonus(userId, contestId)) {
      throw new ResourceBonusAlreadyClaimedException();
    }
    var boost = boostService.boost(userId, contestId);
    var daysSinceSubscription = contestSubscriptionService.daysSinceContestSubscription(userId, contestId).get();
    var diamondsBonus = resourceProperties
      .dailyDiamondsBonus()
      .get(Math.min(daysSinceSubscription, resourceProperties.dailyDiamondsBonus().size() - 1))
      .longValue();
    var livesBonus = resourceProperties
      .dailyLivesBonus()
      .get(Math.min(daysSinceSubscription, resourceProperties.dailyLivesBonus().size() - 1))
      .longValue();
    var diamondsTransaction = resourceRepository
      .alterByUserAndContest(userId, contestId, DIAMONDS, diamondsBonus + boost.diamonds(), BONUS);
    var livesTransaction = resourceRepository.alterByUserAndContest(userId, contestId, LIVES,
      livesBonus + boost.lives(), BONUS);
    resourcePublisher.publish(new ResourceTransactionCreated(livesTransaction));
    resourcePublisher.publish(new ResourceTransactionCreated(diamondsTransaction));
  }

}
