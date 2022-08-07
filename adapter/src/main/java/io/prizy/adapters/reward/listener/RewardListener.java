package io.prizy.adapters.reward.listener;

import io.prizy.domain.reward.event.RewardCreated;
import io.prizy.domain.reward.notifier.RewardNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 14:38
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardListener {

  private final RewardNotifier rewardNotifier;

  @TransactionalEventListener
  public void onRewardCreated(RewardCreated event) {
    rewardNotifier.notifyUser(event.reward());
  }

}
