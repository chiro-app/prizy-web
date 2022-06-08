package io.prizy.domain.referral.usecase;

import java.util.UUID;

import io.prizy.domain.referral.event.ReferralConfirmed;
import io.prizy.domain.referral.ports.ReferralPublisher;
import io.prizy.domain.referral.ports.ReferralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:48
 */

@Component
@RequiredArgsConstructor
public class ConfirmReferralCodeUseCase {

  private final ReferralRepository referralRepository;
  private final ReferralPublisher referralPublisher;

  @Transactional
  public Boolean confirm(UUID userId, UUID referralId) {
    var referrerNode = referralRepository.byUserId(userId);
    if (referrerNode.isEmpty()) {
      return false;
    }
    var referralNode = referralRepository.byUserId(referralId);
    if (referralNode.isEmpty()) {
      return false;
    }
    referralRepository.save(referralNode.get().withConfirmed(true));
    referralPublisher.publish(new ReferralConfirmed(referralId, userId));
    return true;
  }

}
