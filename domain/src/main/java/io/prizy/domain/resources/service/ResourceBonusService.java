package io.prizy.domain.resources.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import io.prizy.domain.contest.exception.UserNotSubscribedException;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import io.prizy.domain.resources.event.ResourceTransactionCreated;
import io.prizy.domain.resources.exception.ResourceBonusAlreadyClaimedException;
import io.prizy.domain.resources.model.ResourceBalance;
import io.prizy.domain.resources.model.ResourceBonusStatus;
import io.prizy.domain.resources.model.ResourceTransaction;
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

  private final ResourceRepository repository;
  private final ContestSubscriptionRepository contestSubscriptionRepository;
  private final ResourcePublisher publisher;
  private final ResourceBoostService boostService;
  private final ResourceProperties properties;

  public void claimKeysDailyBonus(UUID userId) {
    if (!hasAvailableKeysBonus(userId)) {
      throw new ResourceBonusAlreadyClaimedException();
    }
    var transaction = repository.alterKeys(userId, properties.dailyKeysBonus().longValue(), BONUS);
    publisher.publish(new ResourceTransactionCreated(transaction));
  }

  public void claimContestDailyBonus(UUID userId, UUID contestId) {
    if (!contestSubscriptionRepository.userSubscribedTo(userId, contestId)) {
      throw new UserNotSubscribedException(userId, contestId);
    }
    if (!hasAvailableContestBonus(userId, contestId)) {
      throw new ResourceBonusAlreadyClaimedException();
    }
    var boost = boostService.boost(userId, contestId);
    var daysSinceSubscription = daysSinceContestSubscription(userId, contestId)
      .orElseThrow(() -> new UserNotSubscribedException(userId, contestId));
    var diamondsBonus = properties
      .dailyDiamondsBonus()
      .get(Math.min(daysSinceSubscription, properties.dailyDiamondsBonus().size() - 1))
      .longValue();
    var livesBonus = properties
      .dailyLivesBonus()
      .get(Math.min(daysSinceSubscription, properties.dailyLivesBonus().size() - 1))
      .longValue();
    var diamondsTransaction = repository
      .alterByUserAndContest(userId, contestId, DIAMONDS, diamondsBonus + boost.lives(), BONUS);
    var livesTransaction = repository.alterByUserAndContest(userId, contestId, LIVES,
      livesBonus + boost.diamonds(), BONUS);
    publisher.publish(new ResourceTransactionCreated(livesTransaction));
    publisher.publish(new ResourceTransactionCreated(diamondsTransaction));
  }

  public Boolean hasAvailableKeysBonus(UUID userId) {
    return getAvailableKeysBonus(userId).isPresent();
  }

  public ResourceBonusStatus availableKeysBonus(UUID userId) {
    return getAvailableKeysBonus(userId)
      .map(bonus -> ResourceBonusStatus.builder()
        .available(true)
        .availableAt(Optional.empty())
        .build()
      )
      .orElseGet(() -> ResourceBonusStatus.builder()
        .available(false)
        .availableAt(repository
          .lastTransaction(userId)
          .map(ResourceTransaction::dateTime)
          .map(dateTime -> dateTime.plus(1, DAYS))
        )
        .build()
      );
  }

  public Boolean hasAvailableContestBonus(UUID userId, UUID contestId) {
    return getAvailableContestBonus(userId, contestId).isPresent();
  }

  public ResourceBonusStatus availableContestBonus(UUID userId, UUID contestId) {
    return getAvailableContestBonus(userId, contestId)
      .map(bonus -> ResourceBonusStatus.builder()
        .available(true)
        .availableAt(Optional.empty())
        .build()
      )
      .orElseGet(() -> ResourceBonusStatus.builder()
        .available(false)
        .availableAt(repository
          .lastTransaction(userId)
          .map(ResourceTransaction::dateTime)
          .map(dateTime -> dateTime.plus(1, DAYS))
        )
        .build()
      );
  }

  public Optional<ResourceBalance.Absolute> getAvailableKeysBonus(UUID userId) {
    var midnight = Instant.now().truncatedTo(DAYS);
    var midnightPlusOneDay = midnight.plus(1, DAYS);
    var bonusTransactionSinceMidnight = repository
      .byUserIdAndTypeAndCurrencyAndDateTimeBetween(userId, BONUS, KEYS, midnight, midnightPlusOneDay);
    if (bonusTransactionSinceMidnight.isEmpty()) {
      return Optional.of(new ResourceBalance.Absolute(userId, properties.dailyKeysBonus()));
    }
    return Optional.empty();
  }

  public Optional<ResourceBalance.ContestDependent> getAvailableContestBonus(UUID userId, UUID contestId) {
    var midnight = Instant.now().truncatedTo(DAYS);
    var midnightPlusOneDay = midnight.plus(1, DAYS);
    var bonusTransactionSinceMidnight = Stream.concat(
      repository.byUserIdAndTypeAndCurrencyAndDateTimeBetween(userId, BONUS, LIVES, midnight, midnightPlusOneDay).stream(),
      repository.byUserIdAndTypeAndCurrencyAndDateTimeBetween(userId, BONUS, DIAMONDS, midnight, midnightPlusOneDay).stream()
    ).toList();
    if (bonusTransactionSinceMidnight.isEmpty()) {
      return daysSinceContestSubscription(userId, contestId)
        .map(daysSinceSubscription -> ResourceBalance.ContestDependent.builder()
          .userId(userId)
          .contestId(contestId)
          .lives(properties
            .dailyLivesBonus()
            .get(Math.min(daysSinceSubscription, properties.dailyLivesBonus().size() - 1))
          )
          .diamonds(properties
            .dailyDiamondsBonus()
            .get(Math.min(daysSinceSubscription, properties.dailyDiamondsBonus().size() - 1))
            .longValue()
          )
          .build()
        );
    }
    return Optional.empty();
  }

  public void creditReferralBonus(UUID userId) {
    repository.alterKeys(userId, properties.referralKeysBonus().longValue(), TransactionType.CREDIT);
  }

  public void creditReferrerBonus(UUID userId) {
    repository.alterKeys(userId, properties.referrerKeysBonus().longValue(), TransactionType.CREDIT);
  }

  private Optional<Integer> daysSinceContestSubscription(UUID userId, UUID contestId) {
    return contestSubscriptionRepository
      .subscriptionOfUser(userId, contestId)
      .map(subscription -> (int) Duration.between(subscription.dateTime(), Instant.now()).toDays());
  }

}
