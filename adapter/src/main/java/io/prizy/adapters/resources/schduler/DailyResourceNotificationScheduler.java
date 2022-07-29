package io.prizy.adapters.resources.schduler;

import io.prizy.domain.contest.model.ContestSubscription;
import io.prizy.domain.contest.service.ContestService;
import io.prizy.domain.contest.service.ContestSubscriptionService;
import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
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

  private static final String PUSH_NOTIFICATION_SUBJECT = "Ding Ding Ding \uD83D\uDC8E\n";
  private static final String PUSH_NOTIFICATION_CONTENT = "Tes diamants et tes vies sont prêts à être récoltés " +
    "\uD83E\uDD29";

  private final NotificationPublisher notificationPublisher;
  private final ContestSubscriptionService subscriptionService;
  private final ContestService contestService;

  @Scheduled(cron = "0 0 9 * * *") // Every day at 9 am
  public void notifyForDailyResourceBonus() {
    var subscribedUsersToActiveContests = contestService
      .activeContests()
      .stream()
      .flatMap(contest -> subscriptionService.ofContest(contest.id()).stream())
      .map(ContestSubscription::userId)
      .distinct()
      .toList();
    var notification = PushNotification.MultipleUsers.builder()
      .userIds(subscribedUsersToActiveContests)
      .subject(PUSH_NOTIFICATION_SUBJECT)
      .content(PUSH_NOTIFICATION_CONTENT)
      .build();
    notificationPublisher.publishPushNotification(new SendPushNotification(notification));
  }

}
