package io.prizy.adapters.resources.schduler;

import io.prizy.adapters.resources.notifier.ResourceBonusNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 29/07/2022 18:51
 */


@Component
@RequiredArgsConstructor
public class DailyResourceNotificationScheduler {

  private final ResourceBonusNotifier notifier;

  @Scheduled(cron = "0 0 9 * * *", zone = "CET") // Every day at 9 am
  public void notifyForDailyResourceBonus() {
    notifier.notifyForBonus();
  }

}
