package io.prizy.domain.resources.service;

import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.resources.model.ResourceTransaction;
import io.prizy.domain.resources.model.TransactionType;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.ports.ResourceRepository;
import io.prizy.domain.resources.properties.ResourceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:34 AM
 */

@Slf4j
@Transactional
@RequiredArgsConstructor
public class ResourceService {

  private final ContestRepository contestRepository;
  private final ResourceRepository resourceRepository;

  private final ResourceProperties properties;

  public Long getKeyCount(UUID userId) {
    return resourceRepository.countKeys(userId);
  }

  public void debitContestSubscriptionFees(UUID userId, UUID contestId) {
    log.info("Debiting contest subscription fees for user {} for contest {}", userId, contestId);
    var contest = contestRepository
      .byId(contestId)
      .orElseThrow(() -> new ContestNotFoundException(contestId));
    depositKeys(userId, contest.cost().longValue());
  }

  public void creditReferralKeyBonus(UUID userId) {
    depositKeys(userId, properties.referralKeyBonus().longValue());
  }

  public void creditReferrerKeyBonus(UUID userId) {
    depositKeys(userId, properties.referrerKeyBonus().longValue());
  }

  private void depositKeys(UUID userId, Long amount) {
    var transaction = new ResourceTransaction.Absolute(
      null, Currency.KEYS, TransactionType.DEPOSIT,
      amount, userId, Instant.now()
    );
    resourceRepository.saveTransaction(transaction);
  }
}
