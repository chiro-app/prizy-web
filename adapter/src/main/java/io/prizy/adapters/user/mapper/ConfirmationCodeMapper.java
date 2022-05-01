package io.prizy.adapters.user.mapper;

import io.prizy.adapters.user.persistence.entity.ConfirmationCodeEntity;
import io.prizy.domain.user.model.ConfirmationCode;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 12:52 PM
 */


@UtilityClass
public class ConfirmationCodeMapper {

  public ConfirmationCode map(ConfirmationCodeEntity entity) {
    return ConfirmationCode.builder()
      .code(entity.getCode())
      .userId(entity.getUserId())
      .build();
  }

  public ConfirmationCodeEntity map(ConfirmationCode confirmationCode) {
    return ConfirmationCodeEntity.builder()
      .code(confirmationCode.code())
      .userId(confirmationCode.userId())
      .build();
  }

}
