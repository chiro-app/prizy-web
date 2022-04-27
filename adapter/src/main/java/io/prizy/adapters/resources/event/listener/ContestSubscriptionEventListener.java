package io.prizy.adapters.resources.event.listener;

import io.prizy.domain.contest.event.ContestSubscriptionCreated;
import io.prizy.domain.resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ContestSubscriptionEventListener {

  private final ResourceService resourceService;

  @Async
  @TransactionalEventListener
  public void onContestSubscriptionCreated(ContestSubscriptionCreated event) {
    resourceService.debitContestSubscriptionFees(event.subscription().userId(), event.subscription().contestId());
  }

}
