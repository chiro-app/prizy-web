package io.prizy.domain.resources.ports;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.ResourceTransaction;
import io.prizy.domain.resources.model.TransactionType;

public interface ResourceRepository {
  Integer countKeys(UUID userId);

  Integer countLives(UUID userId, UUID contestId);

  Long countDiamonds(UUID userId, UUID contestId);

  Optional<ResourceTransaction> lastTransaction(UUID userId);

  ResourceTransaction alterKeys(UUID userId, Long amount, TransactionType txType);

  ResourceTransaction alterByUserAndContest(UUID userId, UUID contestId, Currency currency, Long amount,
                                            TransactionType txType);

  ResourceTransaction saveTransaction(ResourceTransaction transaction);

  Collection<ResourceTransaction> byUserIdAndContestIdAndTypeAndDateTimeBetweenAndCurrencyIn(
    UUID userId, UUID contestId, TransactionType type, Instant from, Instant to, Collection<Currency> currency);

  Collection<ResourceTransaction> byUserIdAndTypeAndCurrencyAndDateTimeBetween(
    UUID userId, TransactionType type, Currency currency, Instant from, Instant to);
}
