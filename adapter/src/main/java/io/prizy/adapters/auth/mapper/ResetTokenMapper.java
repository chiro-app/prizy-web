package io.prizy.adapters.auth.mapper;

import io.prizy.adapters.auth.persistence.entity.ResetTokenEntity;
import io.prizy.domain.auth.model.ResetToken;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:40 AM
 */


@UtilityClass
public class ResetTokenMapper {

  public ResetTokenEntity map(ResetToken resetTok) {
    return ResetTokenEntity.builder()
      .token(resetTok.token())
      .userId(resetTok.userId())
      .build();
  }

  public ResetToken map(ResetTokenEntity resetCodeEntity) {
    return ResetToken.builder()
      .token(resetCodeEntity.getToken())
      .userId(resetCodeEntity.getUserId())
      .build();
  }

}
