package io.prizy.domain.user.mapper;

import io.prizy.domain.user.model.UpdateUser;
import io.prizy.domain.user.model.User;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:06 PM
 */


@UtilityClass
public class UpdateUserMapper {

  public User map(UpdateUser update) {
    return User.builder()
      .id(update.id())
      .username(update.username())
      .email(update.email())
      .firstName(update.firstName())
      .lastName(update.lastName())
      .gender(update.gender())
      .mobilePhone(update.mobilePhone())
      .birthDate(update.birthDate())
      .avatarAssetId(update.avatarAssetId())
      .addressId(update.addressId())
      .build();
  }

}
