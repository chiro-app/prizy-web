package io.prizy.domain.referral.ports;

import io.prizy.domain.referral.event.ReferralConfirmed;
import io.prizy.domain.referral.event.ReferralCreated;

public interface ReferralPublisher {
  void publish(ReferralCreated event);
  void publish(ReferralConfirmed event);
}
