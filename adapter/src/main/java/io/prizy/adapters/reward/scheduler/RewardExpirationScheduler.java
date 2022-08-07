package io.prizy.adapters.reward.scheduler;

import io.prizy.domain.referral.notifier.ExpiringRewardNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 14:36
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class RewardExpirationScheduler {

  private final ExpiringRewardNotifier notifier;

  @Scheduled(cron = "0 0 9 * * *", zone = "CET") // 9 a.m every day
  public void lookForExpiringRewards() {
    log.info("Looking for expiring coupon rewards");
    notifier.notifyForExpiringRewards();
  }

}
