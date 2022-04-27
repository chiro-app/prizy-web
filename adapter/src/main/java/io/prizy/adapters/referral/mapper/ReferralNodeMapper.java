package io.prizy.adapters.referral.mapper;

import java.util.Optional;

import io.prizy.adapters.referral.persistence.entity.ReferralNodeEntity;
import io.prizy.domain.referral.model.ReferralNode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReferralNodeMapper {

  public ReferralNode map(ReferralNodeEntity entity) {
    return new ReferralNode(
      entity.getUserId(),
      entity.getCode(),
      entity.getConfirmed(),
      Optional.ofNullable(entity.getReferrerId())
    );
  }

  public ReferralNodeEntity map(ReferralNode referralNode) {
    return ReferralNodeEntity.builder()
      .userId(referralNode.userId())
      .code(referralNode.code())
      .confirmed(referralNode.confirmed())
      .referrerId(referralNode.referrerId().orElse(null))
      .build();
  }

}
