package io.prizy.domain.resources.service;

import java.util.UUID;

import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.ResourceBalance;
import io.prizy.domain.resources.model.TransactionType;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:34 AM
 */

@Slf4j
@Transactional
@RequiredArgsConstructor
public class ResourceService {

  private final ResourceRepository repository;
  private final ContestRepository contestRepository;

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
    repository.alterByUserAndContest(userId, contestId, Currency.DIAMONDS, diamonds, TransactionType.CREDIT);
  }

  public void creditLives(UUID userId, UUID contestId, Integer lives) {
    repository.alterByUserAndContest(userId, contestId, Currency.LIVES, Long.valueOf(lives), TransactionType.CREDIT);
  }

  public void debitContestSubscriptionFees(UUID userId, UUID contestId) {
    log.info("Debiting contest subscription fees for user {} for contest {}", userId, contestId);
    var contest = contestRepository
      .byId(contestId)
      .orElseThrow(() -> new ContestNotFoundException(contestId));
    repository.alterKeys(userId, contest.cost().longValue(), TransactionType.DEBIT);
  }
}
