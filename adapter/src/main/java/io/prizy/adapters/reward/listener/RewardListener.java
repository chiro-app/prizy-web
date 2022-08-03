package io.prizy.adapters.reward.listener;

import java.util.Map;

import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.contest.service.ContestService;
import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.reward.event.RewardCreated;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.service.UserService;
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

  private static final String REWARD_EMAIL_SUBJECT = "Ton cadeau %s t'attends ! \uD83E\uDD73";
  private static final String REWARD_EMAIL_TEMPLATE_NAME = "rewarding-email.html";
  private static final String PRODUCT_PUSH_NOTIFICATION_SUBJECT = "Félicitations ! Tu as remporté le concours {{ " +
    "merchantName }} " +
    "\uD83C\uDF89";
  private static final String PRODUCT_PUSH_NOTIFICATION_CONTENT = "Viens vite récupérer ta récompense \uD83C\uDF81";
  private static final String COUPON_PUSH_NOTIFICATION_SUBJECT = "Bravo ! Tu viens de remporter {{ couponName }} chez" +
    " {{ merchantName }} !";
  private static final String COUPON_PUSH_NOTIFICATION_CONTENT = "Profites en dès maintenant ! \uD83C\uDF81";

  private final NotificationPublisher notificationPublisher;
  private final ContestService contestService;
  private final UserService userService;

  @TransactionalEventListener
  public void onRewardCreated(RewardCreated event) {
    var contest = contestService.byPackId(event.reward().packId()).get();
    var user = userService.byId(event.reward().userId()).get();
    var pack = contestService.packById(event.reward().packId()).get();
    log.info("Affected reward {} to user {} on contests {}", event.reward(), user.id(), contest.id());
    sendEmail(user, contest);
    sendPushNotification(user, contest, pack);
  }

  private void sendEmail(User user, Contest contest) {
    notificationPublisher.publishEmail(new SendEmail(new EmailNotification(
      user.id(),
      String.format(REWARD_EMAIL_SUBJECT, contest.merchant().name()),
      REWARD_EMAIL_TEMPLATE_NAME,
      Map.of(
        "firstName", user.firstName(),
        "merchantName", contest.merchant().name()
      )
    )));
  }

  private void sendPushNotification(User user, Contest contest, Pack pack) {
    var builder = PushNotification.SingleUser
      .builder()
      .userId(user.id());
    if (pack instanceof Pack.Product) {
      builder
        .subject(PRODUCT_PUSH_NOTIFICATION_SUBJECT)
        .content(PRODUCT_PUSH_NOTIFICATION_CONTENT)
        .data(Map.of(
          "merchantName", contest.merchant().name()
        ));
    } else if (pack instanceof Pack.Coupon coupon) {
      builder
        .subject(COUPON_PUSH_NOTIFICATION_SUBJECT)
        .content(COUPON_PUSH_NOTIFICATION_CONTENT)
        .data(Map.of(
          "merchantName", contest.merchant().name(),
          "couponName", coupon.name()
        ));
    }
    notificationPublisher.publishPushNotification(new SendPushNotification(builder.build()));
  }

}
