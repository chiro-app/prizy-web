package io.prizy.adapters.reward.scheduler;

import io.prizy.domain.reward.service.RewardService;
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
public class RewardAffectionScheduler {

  private final RewardService rewardService;

  @Scheduled(cron = "0 5 * * * *") // every hour
  public void scheduleRewardAffection() {
    log.info("Looking for finished contests waiting for reward attribution");
    rewardService.affectRewardsToFinishedContests();
  }

}
