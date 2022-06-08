package io.prizy.domain.referral.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.referral.model.ReferralNode;
import io.prizy.domain.referral.ports.ReferralRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReferralService {

  private static final Integer REFERRAL_CODE_LENGTH = 6;
  private static final String REFERRAL_CODE_CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private final ReferralRepository repository;

  public Collection<ReferralNode> getReferrals(UUID userId) {
    return repository.byReferrerId(userId);
  }

  public Optional<ReferralNode> getReferrer(UUID userId) {
    return repository.referrer(userId);
  }

  public Optional<ReferralNode> byId(UUID userId) {
    return repository.byUserId(userId);
  }

  @Transactional
  public String referralCode(UUID userId) {
    return repository
      .byUserId(userId)
      .orElseGet(() -> repository.save(new ReferralNode(
        userId,
        RandomStringUtils.random(REFERRAL_CODE_LENGTH, REFERRAL_CODE_CHAR_POOL),
        false,
        Optional.empty()
      )))
      .code();
  }

  public String generateRandomReferralCode() {
    return RandomStringUtils.random(REFERRAL_CODE_LENGTH, REFERRAL_CODE_CHAR_POOL);
  }
}
