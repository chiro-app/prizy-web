package io.prizy.adapters.auth.mapper;

import io.prizy.adapters.auth.persistence.entity.ResetCodeEntity;
import io.prizy.domain.auth.model.ResetCode;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:40 AM
 */


@UtilityClass
public class ResetCodeMapper {

  public ResetCodeEntity map(ResetCode resetCode) {
    return ResetCodeEntity.builder()
      .code(resetCode.code())
      .created(resetCode.created())
      .userId(resetCode.userId())
      .build();
  }

  public ResetCode map(ResetCodeEntity resetCodeEntity) {
    return ResetCode.builder()
      .code(resetCodeEntity.getCode())
      .created(resetCodeEntity.getCreated())
      .userId(resetCodeEntity.getUserId())
      .build();
  }

}
