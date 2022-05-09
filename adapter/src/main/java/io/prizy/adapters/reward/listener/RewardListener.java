package io.prizy.adapters.reward.listener;

import java.util.Map;

import io.prizy.domain.contest.service.ContestService;
import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.reward.event.RewardCreated;
import io.prizy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 14:38
 */

@Component
@RequiredArgsConstructor
public class RewardListener {

  private static final String REWARD_EMAIL_SUBJECT = "Ton cadeau %s t'attends ! \uD83E\uDD73";
  private static final String REWARD_EMAIL_TEMPLATE_NAME = "rewarding-email.html";

  private final NotificationPublisher notificationPublisher;
  private final ContestService contestService;
  private final UserService userService;

  @TransactionalEventListener
  public void onRewardCreated(RewardCreated event) {
    var contest = contestService.byPackId(event.reward().packId()).get();
    var user = userService.byId(event.reward().userId()).get();
    notificationPublisher.publish(new SendEmail(new EmailNotification(
      event.reward().userId(),
      String.format(REWARD_EMAIL_SUBJECT, contest.merchant().name()),
      REWARD_EMAIL_TEMPLATE_NAME,
      Map.of(
        "firstName", user.firstName(),
        "merchantName", contest.merchant().name()
      )
    )));
  }

}
