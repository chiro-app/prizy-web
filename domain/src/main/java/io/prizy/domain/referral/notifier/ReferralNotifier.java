package io.prizy.domain.referral.notifier;

import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.referral.event.ReferralCreated;
import io.prizy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 8/7/2022 11:54 AM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class ReferralNotifier {

  private static final String PUSH_NOTIFICATION_SUBJECT = "Et un de plus !";
  private static final String PUSH_NOTIFICATION_CONTENT = "Félicitations ton code parrain a été activé ! Viens vite " +
    "récupérer ta récompense \uD83E\uDD17";

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
