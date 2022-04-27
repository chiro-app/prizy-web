package io.prizy.adapters.contest.mapper;


import io.prizy.adapters.contest.persistence.entity.ContestSubscriptionEntity;
import io.prizy.domain.contest.model.ContestSubscription;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContestSubscriptionMapper {

  public ContestSubscription map(ContestSubscriptionEntity entity) {
    return new ContestSubscription(
      entity.getId(),
      entity.getUserId(),
      entity.getContestId(),
      entity.getDateTime()
    );
  }

  public ContestSubscriptionEntity map(ContestSubscription domain) {
    return ContestSubscriptionEntity.builder()
      .id(domain.id())
      .userId(domain.userId())
      .contestId(domain.contestId())
      .dateTime(domain.dateTime())
      .build();
  }

}
