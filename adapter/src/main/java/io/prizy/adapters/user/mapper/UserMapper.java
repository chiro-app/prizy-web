package io.prizy.adapters.user.mapper;

import java.util.Optional;

import io.prizy.adapters.user.persistence.entity.UserEntity;
import io.prizy.domain.user.model.User;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:30 PM
 */


@UtilityClass
public class UserMapper {

  public UserEntity map(User user) {
    return UserEntity.builder()
      .id(user.id())
      .email(user.email())
      .username(user.username())
      .passwordHash(user.passwordHash())
      .firstName(user.firstName())
      .lastName(user.lastName())
      .gender(user.gender())
      .birthDate(user.birthDate())
      .avatarMediaId(user.avatarMediaId().orElse(null))
      .mobilePhone(user.mobilePhone().orElse(null))
      .status(user.status())
      .created(user.created())
      .addressId(user.addressId().orElse(null))
      .build();
  }

  public User map(UserEntity entity) {
    return User.builder()
      .id(entity.getId())
      .email(entity.getEmail())
      .username(entity.getUsername())
      .passwordHash(entity.getPasswordHash())
      .firstName(entity.getFirstName())
      .lastName(entity.getLastName())
      .gender(entity.getGender())
      .birthDate(entity.getBirthDate())
      .avatarMediaId(Optional.ofNullable(entity.getAvatarMediaId()))
      .mobilePhone(Optional.ofNullable(entity.getMobilePhone()))
      .status(entity.getStatus())
      .created(entity.getCreated())
      .addressId(Optional.ofNullable(entity.getAddressId()))
      .build();
  }

}
