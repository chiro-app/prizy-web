package io.prizy.domain.referral.notifier;

import java.util.UUID;

import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 8/7/2022 11:54 AM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class ReferralNotifier {

  private static final String PUSH_NOTIFICATION_SUBJECT = "Félicitations ton code parrain a été activé ! " +
    "\uD83C\uDF89\uD83E\uDD17";
  private static final String PUSH_NOTIFICATION_CONTENT = "Pour te récompenser nous t'offrons 6 clés \uD83D\uDD11";

  private final NotificationPublisher notificationPublisher;

  public void notifyReferrer(UUID referrerId) {
    var push = PushNotification.SingleUser.builder()
      .userId(referrerId)
      .subject(PUSH_NOTIFICATION_SUBJECT)
      .content(PUSH_NOTIFICATION_CONTENT)
      .build();
    notificationPublisher.publishPushNotification(new SendPushNotification(push));
  }

}
