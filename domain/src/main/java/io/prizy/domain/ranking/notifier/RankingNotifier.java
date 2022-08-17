package io.prizy.domain.ranking.notifier;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import io.prizy.domain.contest.service.ContestService;
import io.prizy.domain.notification.event.SendPushNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 8/8/2022 11:21 PM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class RankingNotifier {

  private static final String PUSH_NOTIFICATION_SUBJECT = "Mince ! Tu viens de te faire dépasser !";
  private static final String PUSH_NOTIFICATION_CONTENT = "Reviens vite te positionner pour gagner ta récompense " +
    "\uD83D\uDCAA";

  private final RankingService rankingService;
  private final ContestService contestService;
  private final NotificationPublisher notificationPublisher;

  public void notifyDerankingUsers(UUID contestId, UUID userId, Optional<Integer> previousRank) {
    var userIds = getDerankingUsers(contestId, userId, previousRank);
    var push = PushNotification.MultipleUsers.builder()
      .userIds(userIds)
      .subject(PUSH_NOTIFICATION_SUBJECT)
      .content(PUSH_NOTIFICATION_CONTENT)
      .build();
    notificationPublisher.publishPushNotification(new SendPushNotification(push));
  }

  private Set<UUID> getDerankingUsers(UUID contestId, UUID userId, Optional<Integer> previousRank) {
    var rows = rankingService
      .getForContest(contestId)
      .rows()
      .stream()
      .toList();
    var usersRow = rows.stream()
      .filter(row -> row.userId().equals(userId))
      .findAny()
      .get();
    var usersRank = rows.indexOf(usersRow);
    var contest = contestService.byId(contestId).get();
    var shiftingMilestones = contest.packs()
      .stream()
//      .filter(pack -> pack.lastWinnerPosition() >= usersRank)
      .filter(pack -> pack.lastWinnerPosition() > usersRank)
      .map(pack -> pack.lastWinnerPosition() - 1)
      .toList();
    var userIds = shiftingMilestones
      .stream()
//      .map(milestone -> milestone - 1)
      .filter(milestone -> rows.size() > milestone)
      .filter(milestone -> previousRank.isEmpty() || previousRank.get() > milestone)
      .map(rank -> rows.get(rank).userId())
      .collect(Collectors.toSet());
    return userIds;
  }

}
