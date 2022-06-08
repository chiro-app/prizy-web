package io.prizy.domain.referral.usecase;

import java.util.UUID;

import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.referral.model.ReferralNode;
import io.prizy.domain.referral.ports.ReferralPublisher;
import io.prizy.domain.referral.ports.ReferralRepository;
import io.prizy.domain.referral.service.ReferralService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:48
 */

@Component
@RequiredArgsConstructor
public class SubmitReferralCodeUseCase {

  private final ReferralRepository referralRepository;
  private final ReferralPublisher referralPublisher;
  private final ReferralService referralService;

  @Transactional
  public Boolean submit(UUID userId, String referralCode) {
    var referrerId = referralRepository
      .byReferralCode(referralCode)
      .map(ReferralNode::userId);
    if (referrerId.isEmpty()) {
      return false;
    }
    var referralNode = referralRepository
      .byUserId(userId)
      .orElseGet(() -> referralRepository.save(new ReferralNode(
        userId,
        referralService.generateRandomReferralCode(),
        false,
        referrerId
      )));
    if (
      referralNode.code().equals(referralCode) || // A user can't refer himself
        referralNode.referrerId().isPresent() || // A user can't be referred twice
        referrerId.get().equals(referralNode.userId()) // A user can't refer his referrer
    ) {
      return false;
    }
    referralRepository.save(referralNode.withReferrerId(referrerId));
    referralPublisher.publish(new ReferralCreated(userId, referrerId.get()));
    return true;
  }

}
