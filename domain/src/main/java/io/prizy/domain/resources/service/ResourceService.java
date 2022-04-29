package io.prizy.domain.resources.service;

import java.util.UUID;

import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:34 AM
 */

@Slf4j
@RequiredArgsConstructor
public class ResourceService {

  private final ContestRepository contestRepository;
  private final ResourceRepository resourceRepository;

  public Long getKeyCount(UUID userId) {
    return resourceRepository.countKeys(userId);
  }

  public void debitContestSubscriptionFees(UUID userId, UUID contestId) {
    log.info("Debiting contest subscription fees for user {} for contest {}", userId, contestId);
  }
}
