package io.prizy.domain.referral.usecase;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.referral.model.ReferralNode;
import io.prizy.domain.referral.ports.ReferralPublisher;
import io.prizy.domain.referral.ports.ReferralRepository;
import io.prizy.domain.referral.service.ReferralService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:48
 */

@Slf4j
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
      log.warn("Can't find user with referral code {}", referrerId);
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
    var referralConditions = List.of(
      referralNode.code().equals(referralCode), // A user can't refer himself
        referralNode.referrerId().isPresent(), // A user can't be referred twice
        referrerId.get().equals(referralNode.userId()) // A user can't refer his referrer
    );
    if (referralConditions.stream().anyMatch(condition -> condition)) {
      var unsatisfiedConditionIndices = IntStream.of(0, 1, 2)
          .filter(index -> !referralConditions.get(index))
          .toArray();
      log.warn("Can't satisfy all referral conditions, missing conditions [{}]", unsatisfiedConditionIndices);
      return false;
    }
    referralRepository.save(referralNode.withReferrerId(referrerId));
    referralPublisher.publish(new ReferralCreated(userId, referrerId.get()));
    return true;
  }

}
