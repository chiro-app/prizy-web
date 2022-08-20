package io.prizy.adapters.resources.event.listener;

import io.prizy.domain.contest.event.ContestSubscriptionCreated;
import io.prizy.domain.referral.event.ReferralConfirmed;
import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.resources.service.ResourceBonusService;
import io.prizy.domain.resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceEventListener {

  private final ResourceService resourceService;
  private final ResourceBonusService resourceBonusService;

  @EventListener
  public void onContestSubscriptionCreated(ContestSubscriptionCreated event) {
    resourceService.debitContestSubscriptionFees(event.subscription().userId(), event.subscription().contestId());
  }

  @EventListener
  public void onReferralCreated(ReferralCreated event) {
    resourceBonusService.creditReferralBonus(event.userId());
    resourceBonusService.creditReferralBonus(event.referrerId());
  }

  @EventListener
  public void onReferralConfirmed(ReferralConfirmed event) {
    resourceBonusService.creditReferrerBonus(event.referrerId());
  }

}
