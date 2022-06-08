package io.prizy.adapters.resources.persistence;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.resources.mapper.ResourceTransactionMapper;
import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity;
import io.prizy.adapters.resources.persistence.repository.AbsoluteResourceTransactionJpaRepository;
import io.prizy.adapters.resources.persistence.repository.ContestDependentResourceTransactionJpaRepository;
import io.prizy.adapters.resources.persistence.repository.ResourceTransactionJpaRepository;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.ResourceTransaction;
import io.prizy.domain.resources.model.TransactionType;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepository {

  private final ResourceTransactionJpaRepository abstractJpaRepository;
  private final AbsoluteResourceTransactionJpaRepository absoluteJpaRepository;
  private final ContestDependentResourceTransactionJpaRepository contestDependentJpaRepository;

  @Override
  public Integer countKeys(UUID userId) {
    return absoluteJpaRepository
      .findAbsolutesByCurrencyAndUserId(Currency.KEYS, userId)
      .stream()
      .reduce(
        0L,
        (acc, transaction) -> transaction.getType().apply(acc, transaction.getAmount()),
        Long::sum
      )
      .intValue();
  }

  @Override
  public Integer countLives(UUID userId, UUID contestId) {
    return contestDependentJpaRepository
      .findContestDependentsByCurrencyAndContestIdAndUserId(Currency.LIVES, contestId, userId)
      .stream()
      .reduce(
        0L,
        (acc, transaction) -> transaction.getType().apply(acc, transaction.getAmount()),
        Long::sum
      )
      .intValue();
  }

  @Override
  public Long countDiamonds(UUID userId, UUID contestId) {
    return contestDependentJpaRepository
      .findContestDependentsByCurrencyAndContestIdAndUserId(Currency.DIAMONDS, contestId, userId)
      .stream()
      .reduce(
        0L,
        (acc, transaction) -> transaction.getType().apply(acc, transaction.getAmount()),
        Long::sum
      );
  }

  @Override
  public Optional<ResourceTransaction> lastTransaction(UUID userId) {
    return abstractJpaRepository
      .findTop1ByUserIdOrderByDateTimeDesc(userId)
      .map(ResourceTransactionMapper::map);
  }

  @Override
  public ResourceTransaction saveTransaction(ResourceTransaction transaction) {
    var entity = ResourceTransactionMapper.map(transaction);
    if (entity instanceof ResourceTransactionEntity.Absolute absolute) {
      entity = absoluteJpaRepository.save(absolute);
    } else if (entity instanceof ResourceTransactionEntity.ContestDependent contestDependent) {
      contestDependentJpaRepository.save(contestDependent);
    } else {
      throw new IllegalArgumentException("Unsupported transaction type " + entity.getClass().getName());
    }
    return ResourceTransactionMapper.map(entity);
  }

  @Override
  public Collection<ResourceTransaction> byUserIdAndContestIdAndTypeAndDateTimeBetweenAndCurrencyIn(
    UUID userId, UUID contestId, TransactionType type, Instant from, Instant to, Collection<Currency> currencies) {
    return contestDependentJpaRepository
      .findContestDependentsByUserIdAndContestIdAndTypeAndDateTimeBetweenAndCurrencyIn(userId, contestId, type, from,
        to, currencies)
      .stream()
      .map(ResourceTransactionMapper::map)
      .toList();
  }

  @Override
  public Collection<ResourceTransaction> byUserIdAndTypeAndCurrencyAndDateTimeBetween(
    UUID userId, TransactionType type, Currency currency, Instant from, Instant to) {
    return abstractJpaRepository
      .findAllByUserIdAndTypeAndCurrencyAndDateTimeBetween(userId, type, currency, from, to)
      .stream()
      .map(ResourceTransactionMapper::map)
      .toList();
  }

  @Override
  public ResourceTransaction alterKeys(UUID userId, Long amount, TransactionType txType) {
    var transaction = ResourceTransactionEntity.Absolute.builder()
      .type(txType)
      .amount(amount)
      .userId(userId)
      .dateTime(Instant.now())
      .currency(Currency.KEYS)
      .build();
    return ResourceTransactionMapper.map(absoluteJpaRepository.save(transaction));
  }

  @Override
  public ResourceTransaction alterByUserAndContest(UUID userId, UUID contestId, Currency currency, Long amount,
                                                   TransactionType txType) {
    var transaction = ResourceTransactionEntity.ContestDependent.builder()
      .type(txType)
      .userId(userId)
      .amount(amount)
      .currency(currency)
      .contestId(contestId)
      .dateTime(Instant.now())
      .build();
    return ResourceTransactionMapper.map(contestDependentJpaRepository.save(transaction));
  }

}
