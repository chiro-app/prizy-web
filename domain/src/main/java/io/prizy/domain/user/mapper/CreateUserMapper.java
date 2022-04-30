package io.prizy.domain.user.mapper;

import java.time.Instant;
import java.util.Optional;

import io.prizy.domain.user.model.CreateUser;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.model.UserStatus;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:06 PM
 */


@UtilityClass
public class CreateUserMapper {

  public User map(CreateUser create) {
    return User.builder()
      .username(create.username())
      .email(create.email())
      .firstName(create.firstName())
      .lastName(create.lastName())
      .gender(create.gender())
      .mobilePhone(create.mobilePhone())
      .status(UserStatus.PENDING)
      .created(Instant.now())
      .birthDate(create.birthDate())
      .avatarMediaId(Optional.empty())
      .addressId(Optional.empty())
      .build();
  }

}
