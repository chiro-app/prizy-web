package io.prizy.domain.resources.event.listener;

import io.prizy.domain.contest.event.ContestSubscriptionCreated;
import io.prizy.domain.resources.properties.ResourceProperties;
import io.prizy.domain.resources.service.ResourceBonusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Nidhal Dogga
 * @created 14/06/2022 18:15
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionEventListener {

  private final ResourceBonusService bonusService;
  private final ResourceProperties resourceProperties;

  @TransactionalEventListener
  public void onSubscriptionCreated(ContestSubscriptionCreated event) {
    var subscription = event.subscription();
    log.info("Attributing subscription bonus {}", subscription);
    bonusService.creditContestBonus(
      subscription.userId(),
      subscription.contestId(),
      resourceProperties.initialLivesBonus(),
      resourceProperties.initialDiamondsBonus()
    );
  }

}
