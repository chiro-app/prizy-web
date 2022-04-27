package io.prizy.adapters.referral.event.publisher;


import io.prizy.domain.referral.event.ReferralConfirmed;
import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.referral.ports.ReferralPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringReferralPublisher implements ReferralPublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(ReferralCreated event) {
    aep.publishEvent(event);
  }

  @Override
  public void publish(ReferralConfirmed event) {
    aep.publishEvent(event);
  }

}
