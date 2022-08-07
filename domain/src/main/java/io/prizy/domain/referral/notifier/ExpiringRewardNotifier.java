package io.prizy.domain.referral.notifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.reward.domain.Reward;
import io.prizy.domain.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 8/7/2022 12:36 PM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class ExpiringRewardNotifier {

  private static final String PUSH_NOTIFICATION_SUBJECT = "Ta récompense arrive bientôt à expiration \uD83D\uDE31";
  private static final String PUSH_NOTIFICATION_CONTENT = "Il ne te reste plus que 5 jours pour bénéficier de ta " +
    "réduction. Profites-en dès maintenant !\n";

  private final RewardService rewardService;
  private final NotificationPublisher notificationPublisher;

  public void notifyForExpiringRewards() {
    var expirationDate = Instant.now().plus(5, ChronoUnit.DAYS);
    var usersToNotify = rewardService
      .couponRewardsExpiringBefore(expirationDate)
      .stream()
      .map(Reward::userId)
      .toList();
    var push = PushNotification.MultipleUsers.builder()
      .userIds(usersToNotify)
      .subject(PUSH_NOTIFICATION_SUBJECT)
      .content(PUSH_NOTIFICATION_CONTENT)
      .build();
    notificationPublisher.publishPushNotification(new SendPushNotification(push));
  }

}
