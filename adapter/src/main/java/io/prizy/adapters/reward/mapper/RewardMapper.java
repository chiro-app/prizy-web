package io.prizy.adapters.reward.mapper;

import io.prizy.adapters.reward.persistence.entity.RewardEntity;
import io.prizy.domain.reward.domain.Reward;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 15:01
 */

@UtilityClass
public class RewardMapper {

  public RewardEntity map(Reward reward) {
    return RewardEntity.builder()
      .id(reward.id())
      .packId(reward.packId())
      .userId(reward.userId())
      .created(reward.created())
      .build();
  }

  public Reward map(RewardEntity rewardEntity) {
    return Reward.builder()
      .id(rewardEntity.getId())
      .packId(rewardEntity.getPackId())
      .userId(rewardEntity.getUserId())
      .created(rewardEntity.getCreated())
      .build();
  }

}
