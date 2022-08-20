package io.prizy.adapters.resources.notifier;

import java.util.stream.Collectors;

import io.prizy.domain.contest.model.ContestSubscription;
import io.prizy.domain.contest.service.ContestService;
import io.prizy.domain.contest.service.ContestSubscriptionService;
import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.resources.service.ResourceBonusService;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 8/7/2022 12:19 PM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceBonusNotifier {

  private static final String CONTEST_BONUS_PUSH_NOTIFICATION_SUBJECT = "Ding Ding Ding \uD83D\uDC8E\n";
  private static final String CONTEST_BONUS_PUSH_NOTIFICATION_CONTENT = "Tes diamants et tes vies sont prêts à être " +
    "récoltés " +
    "\uD83E\uDD29";

  private static final String KEYS_BONUS_PUSH_NOTIFICATION_SUBJECT = "Tes clés sont arrivées ! \uD83D\uDE0A";
  private static final String KEYS_BONUS_PUSH_NOTIFICATION_CONTENT = "Viens vite les récupérer pour participer aux " +
    "nouveaux" +
    " concours.";

  private final NotificationPublisher notificationPublisher;
  private final ContestSubscriptionService subscriptionService;
  private final ContestService contestService;
  private final ResourceBonusService bonusService;
  private final UserService userService;

  public void notifyForBonus() {
    notifyForContestBonuses();
    notifyForKeys();
  }

  private void notifyForContestBonuses() {
    var subscribedUsersToActiveContests = contestService
      .activeContests()
      .stream()
      .flatMap(contest -> subscriptionService.ofContest(contest.id()).stream())
      .filter(subscription -> bonusService.hasAvailableContestBonus(subscription.userId(), subscription.contestId()))
      .map(ContestSubscription::userId)
      .collect(Collectors.toSet());
    var notification = PushNotification.MultipleUsers.builder()
      .userIds(subscribedUsersToActiveContests)
      .subject(CONTEST_BONUS_PUSH_NOTIFICATION_SUBJECT)
      .content(CONTEST_BONUS_PUSH_NOTIFICATION_CONTENT)
      .build();
    notificationPublisher.publishPushNotification(new SendPushNotification(notification));
  }

  private void notifyForKeys() {
    var usersWithPendingKeysBonus = userService.listUsers()
      .stream()
      .map(User::id)
      .filter(bonusService::hasAvailableKeysBonus)
      .collect(Collectors.toSet());
    var notification = PushNotification.MultipleUsers.builder()
      .userIds(usersWithPendingKeysBonus)
      .subject(KEYS_BONUS_PUSH_NOTIFICATION_SUBJECT)
      .content(KEYS_BONUS_PUSH_NOTIFICATION_CONTENT)
      .build();
    notificationPublisher.publishPushNotification(new SendPushNotification(notification));
  }

}
