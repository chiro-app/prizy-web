package io.prizy.domain.resources.service;

import java.util.UUID;

import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.resources.event.ResourceTransactionCreated;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.ResourceBalance;
import io.prizy.domain.resources.model.TransactionType;
import io.prizy.domain.resources.ports.ResourcePublisher;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:34 AM
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ResourceService {

  private final ResourceRepository repository;
  private final ContestRepository contestRepository;
  private final ResourcePublisher resourcePublisher;

  public ResourceBalance.Absolute getAbsoluteBalance(UUID userId) {
    return ResourceBalance.Absolute.builder()
      .userId(userId)
      .keys(repository.countKeys(userId))
      .build();
  }

  public ResourceBalance.ContestDependent getContestDependentBalance(UUID userId, UUID contestId) {
    return ResourceBalance.ContestDependent.builder()
      .userId(userId)
      .contestId(contestId)
      .diamonds(repository.countDiamonds(userId, contestId))
      .lives(repository.countLives(userId, contestId))
      .build();
  }

  public void creditDiamonds(UUID userId, UUID contestId, Long diamonds) {
    var transaction = repository
      .alterByUserAndContest(userId, contestId, Currency.DIAMONDS, diamonds, TransactionType.CREDIT);
    resourcePublisher.publish(new ResourceTransactionCreated(transaction));
  }

  public void debitDiamonds(UUID userId, UUID contestId, Long diamonds) {
    var transaction = repository
      .alterByUserAndContest(userId, contestId, Currency.DIAMONDS, diamonds, TransactionType.DEBIT);
    resourcePublisher.publish(new ResourceTransactionCreated(transaction));
  }

  public void debitLives(UUID userId, UUID contestId, Integer lives) {
    var transaction = repository
      .alterByUserAndContest(userId, contestId, Currency.LIVES, Long.valueOf(lives), TransactionType.DEBIT);
    resourcePublisher.publish(new ResourceTransactionCreated(transaction));
  }

  public void debitContestSubscriptionFees(UUID userId, UUID contestId) {
    log.info("Debiting contest subscription fees for user {} for contest {}", userId, contestId);
    var contest = contestRepository
      .byId(contestId)
      .orElseThrow(() -> new ContestNotFoundException(contestId));
    var transaction = repository.alterKeys(userId, contest.cost().longValue(), TransactionType.DEBIT);
    resourcePublisher.publish(new ResourceTransactionCreated(transaction));
  }
}
