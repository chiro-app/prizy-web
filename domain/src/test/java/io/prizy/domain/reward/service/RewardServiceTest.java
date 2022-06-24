package io.prizy.domain.reward.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.ranking.model.RankingRow;
import io.prizy.domain.ranking.port.RankingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Nidhal Dogga
 * @created 24/06/2022 11:43
 */

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

  @Mock
  private RankingRepository rankingRepository;

  @InjectMocks
  private RewardService rewardService;

  @Test
  void shouldAffectRewards() {
    var userIds = IntStream.range(0, 100)
      .mapToObj(i -> UUID.randomUUID())
      .toList();
    Mockito
      .when(rankingRepository.byContestId(Mockito.any()))
      .thenReturn(userIds.stream().map(userId -> RankingRow.builder().userId(userId).build()).toList());
    var contest = Contest.builder()
      .packs(List.of(
        Pack.Product.builder()
          .id(UUID.fromString("ac9a739b-6b48-46d9-b588-dc5b9aae13ff"))
          .name("first product")
          .firstWinnerPosition(1)
          .lastWinnerPosition(2)
          .build(),
        Pack.Coupon.builder()
          .id(UUID.fromString("19f12960-7f50-4965-a56c-fc3454c67c11"))
          .name("first coupon")
          .firstWinnerPosition(2)
          .lastWinnerPosition(5)
          .build(),
        Pack.Product.builder()
          .id(UUID.fromString("7dbb4663-82c2-462a-ba2d-0a4d9c5e29ba"))
          .name("second product")
          .firstWinnerPosition(5)
          .lastWinnerPosition(11)
          .build()
      ))
      .build();
    var rewards = rewardService.affectRewards(contest);
    Assertions.assertThat(rewards.size()).isEqualTo(10);

    var rewardList = rewards.stream().toList();
    var firstReward = rewardList.get(0);
    Assertions.assertThat(firstReward.userId()).isEqualTo(userIds.get(0));
    Assertions.assertThat(firstReward.packId()).isEqualTo(UUID.fromString("ac9a739b-6b48-46d9-b588-dc5b9aae13ff"));

    var secondReward = rewardList.get(1);
    var thirdReward = rewardList.get(2);
    var fourthReward = rewardList.get(3);
    Assertions.assertThat(secondReward.userId()).isEqualTo(userIds.get(1));
    Assertions.assertThat(secondReward.packId()).isEqualTo(UUID.fromString("19f12960-7f50-4965-a56c-fc3454c67c11"));
    Assertions.assertThat(thirdReward.userId()).isEqualTo(userIds.get(2));
    Assertions.assertThat(thirdReward.packId()).isEqualTo(UUID.fromString("19f12960-7f50-4965-a56c-fc3454c67c11"));
    Assertions.assertThat(fourthReward.userId()).isEqualTo(userIds.get(3));
    Assertions.assertThat(fourthReward.packId()).isEqualTo(UUID.fromString("19f12960-7f50-4965-a56c-fc3454c67c11"));

    var fifthReward = rewardList.get(4);
    Assertions.assertThat(fifthReward.userId()).isEqualTo(userIds.get(4));
    Assertions.assertThat(fifthReward.packId()).isEqualTo(UUID.fromString("7dbb4663-82c2-462a-ba2d-0a4d9c5e29ba"));
  }

}