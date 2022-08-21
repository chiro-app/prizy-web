package io.prizy.domain.resources.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.resources.event.ResourceTransactionCreated;
import io.prizy.domain.resources.model.ResourceBalance;
import io.prizy.domain.resources.model.TransactionType;
import io.prizy.domain.resources.ports.ResourcePublisher;
import io.prizy.domain.resources.ports.ResourceRepository;
import io.prizy.domain.resources.properties.ResourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.prizy.domain.resources.model.Currency.DIAMONDS;
import static io.prizy.domain.resources.model.Currency.KEYS;
import static io.prizy.domain.resources.model.Currency.LIVES;
import static io.prizy.domain.resources.model.TransactionType.BONUS;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 14:38
 */


@Service
@Transactional
@RequiredArgsConstructor
public class ResourceBonusService {

  private final ResourceRepository resourceRepository;
  private final ResourceBoostService boostService;
  private final ResourcePublisher resourcePublisher;
  private final ResourceProperties resourceProperties;

  public Boolean hasAvailableKeysBonus(UUID userId) {
    return getAvailableKeysBonus(userId).isPresent();
  }

  public Boolean hasAvailableContestBonus(UUID userId, UUID contestId) {
    return getAvailableContestBonus(userId, contestId).isPresent();
  }

  public Optional<ResourceBalance.Absolute> getAvailableKeysBonus(UUID userId) {
    var midnight = Instant.now().truncatedTo(DAYS);
    var midnightPlusOneDay = midnight.plus(1, DAYS);
    var bonusTransactionSinceMidnight = resourceRepository
      .byUserIdAndTypeAndCurrencyAndDateTimeBetween(userId, BONUS, KEYS, midnight, midnightPlusOneDay);
    if (bonusTransactionSinceMidnight.isEmpty()) {
      return Optional.of(new ResourceBalance.Absolute(userId, resourceProperties.dailyKeysBonus()));
    }
    return Optional.empty();
  }

  public Optional<ResourceBalance.ContestDependent> getAvailableContestBonus(UUID userId, UUID contestId) {
    var midnight = Instant.now().truncatedTo(DAYS);
    var midnightPlusOneDay = midnight.plus(1, DAYS);
    var bonusTransactionSinceMidnight = resourceRepository
      .byUserIdAndContestIdAndTypeAndDateTimeBetweenAndCurrencyIn(userId, contestId, BONUS, midnight,
        midnightPlusOneDay, List.of(LIVES, DIAMONDS))
      .stream()
      .toList();
    var boost = boostService.boost(userId, contestId);
    if (bonusTransactionSinceMidnight.isEmpty()) {
      return Optional.of(ResourceBalance.ContestDependent.builder()
        .userId(userId)
        .contestId(contestId)
        .lives(resourceProperties.dailyLivesBonus() + boost.lives())
        .diamonds(resourceProperties.dailyDiamondsBonus().longValue() + boost.diamonds())
        .build()
      );
    }
    return Optional.empty();
  }

  public void creditContestBonus(UUID userId, UUID contestId, Integer lives, Integer diamonds) {
    var boost = boostService.boost(userId, contestId);
    var diamondsTransaction = resourceRepository
      .alterByUserAndContest(userId, contestId, DIAMONDS, diamonds + boost.diamonds(), BONUS);
    var livesTransaction = resourceRepository
      .alterByUserAndContest(userId, contestId, LIVES, lives.longValue() + boost.lives(), BONUS);
    resourcePublisher
      .publish(new ResourceTransactionCreated(livesTransaction), new ResourceTransactionCreated(diamondsTransaction));
  }

  public void creditReferralBonus(UUID userId) {
    resourceRepository.alterKeys(userId, resourceProperties.referralKeysBonus().longValue(), TransactionType.CREDIT);
  }

}
