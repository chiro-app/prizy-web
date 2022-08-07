package io.prizy.adapters.referral.event.listener;

import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.referral.notifier.ReferralNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 8/7/2022 11:57 AM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class ReferralListener {

  private final ReferralNotifier notifier;

  @EventListener
  public void onReferralCreated(ReferralCreated event) {
    notifier.notifyReferrer(event.referrerId());
  }

}
