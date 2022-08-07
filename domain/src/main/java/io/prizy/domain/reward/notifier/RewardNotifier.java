package io.prizy.domain.reward.notifier;

import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.contest.service.ContestService;
import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.reward.domain.Reward;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Nidhal Dogga
 * @created 8/7/2022 11:52 AM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class RewardNotifier {

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

  public void notifyUser(Reward reward) {
    var contest = contestService.byPackId(reward.packId()).get();
    var user = userService.byId(reward.userId()).get();
    var pack = contestService.packById(reward.packId()).get();
    log.info("Affected reward {} to user {} on contests {}", reward, user.id(), contest.id());
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
