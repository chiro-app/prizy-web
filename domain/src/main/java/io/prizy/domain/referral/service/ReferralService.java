package io.prizy.domain.referral.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.referral.event.ReferralConfirmed;
import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.referral.model.ReferralNode;
import io.prizy.domain.referral.ports.ReferralPublisher;
import io.prizy.domain.referral.ports.ReferralRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
public class ReferralService {

  private static final Integer REFERRAL_CODE_LENGTH = 6;
  private static final String REFERRAL_CODE_CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private final ReferralRepository repository;
  private final ReferralPublisher publisher;

  public Collection<ReferralNode> getReferrals(UUID userId) {
    return repository.byReferrerId(userId);
  }

  public Optional<ReferralNode> getReferrer(UUID userId) {
    return repository.referrer(userId);
  }

  public Boolean submitReferralCode(UUID userId, String referralCode) {
    var referrerId = repository
      .byReferralCode(referralCode)
      .flatMap(ReferralNode::referrerId);
    if (referrerId.isEmpty()) {
      return false;
    }
    var referralNode = repository
      .byUserId(userId)
      .orElseGet(() -> newReferralNode(userId, referrerId.get()));
    if (
      referralNode.code().equals(referralCode) || // A user can't refer himself
        referralNode.referrerId().isPresent() || // A user can't be referred twice
        referrerId.get().equals(referralNode.userId()) // A user can't refer his referrer
    ) {
      return false;
    }
    repository.save(referralNode);
    publisher.publish(new ReferralCreated(userId, referrerId.get()));
    return true;
  }

  public Boolean confirmReferralCode(UUID userId, UUID referralId) {
    var referrerNode = repository.byUserId(userId);
    if (referrerNode.isEmpty()) {
      return false;
    }
    var referralNode = repository.byUserId(referralId);
    if (referralNode.isEmpty()) {
      return false;
    }
    repository.save(referralNode.get().withConfirmed(true));
    publisher.publish(new ReferralConfirmed(referralId, userId));
    return true;
  }

  public Optional<ReferralNode> byId(UUID userId) {
    return repository.byUserId(userId);
  }

  public String referralCode(UUID userId) {
    return repository
      .byUserId(userId)
      .orElseGet(() -> newReferralNode(userId, null))
      .code();
  }

  private ReferralNode newReferralNode(UUID userId, UUID referrerId) {
    return repository.save(new ReferralNode(
      userId,
      RandomStringUtils.random(REFERRAL_CODE_LENGTH, REFERRAL_CODE_CHAR_POOL),
      false,
      Optional.ofNullable(referrerId)
    ));
  }

}
