package io.prizy.domain.contest.usecase;

import java.util.Collection;
import java.util.UUID;

import io.prizy.domain.contest.service.ContestSubscriptionService;
import io.prizy.domain.referral.model.ReferralNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:36
 */


@Component
@RequiredArgsConstructor
public class GetUserSubscribedReferralsUseCase {

  private final ContestSubscriptionService subscriptionService;

  public Collection<ReferralNode> get(UUID contestId, UUID userId) {
    return subscriptionService.subscribedReferrals(contestId, userId);
  }

}
