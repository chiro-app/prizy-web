package io.prizy.adapters.resources.event.listener;

import io.prizy.domain.contest.event.ContestSubscriptionCreated;
import io.prizy.domain.referral.event.ReferralConfirmed;
import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Async
@Component
@RequiredArgsConstructor
public class ResourceEventListener {

  private final ResourceService resourceService;

  @TransactionalEventListener
  public void onContestSubscriptionCreated(ContestSubscriptionCreated event) {
    resourceService.debitContestSubscriptionFees(event.subscription().userId(), event.subscription().contestId());
  }

  @TransactionalEventListener
  public void onReferralCreated(ReferralCreated event) {
    resourceService.creditReferralKeyBonus(event.userId());
  }

  @TransactionalEventListener
  public void onReferralConfirmed(ReferralConfirmed event) {
    resourceService.creditReferrerKeyBonus(event.referrerId());
  }

}
