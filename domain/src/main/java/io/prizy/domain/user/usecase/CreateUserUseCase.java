package io.prizy.domain.user.usecase;

import java.util.List;

import io.prizy.domain.auth.model.Roles;
import io.prizy.domain.user.event.UserCreated;
import io.prizy.domain.user.exception.UserExistsException;
import io.prizy.domain.user.mapper.CreateUserMapper;
import io.prizy.domain.user.model.CreateUser;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.port.PasswordHasher;
import io.prizy.domain.user.port.UserPublisher;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:59
 */

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {

  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;
  private final UserPublisher userPublisher;

  @Transactional
  public User create(CreateUser create) {
    var userExists = userRepository.existsByEmailPhoneOrUsername(
      create.email(),
      create.username(),
      create.mobilePhone().orElse(null)
    );
    if (userExists) {
      throw new UserExistsException();
    }
    var user = userRepository.save(CreateUserMapper
      .map(create)
      .withRoles(List.of(Roles.USER))
      .withPasswordHash(passwordHasher.hash(create.password()))
    );
    userPublisher.publish(new UserCreated(user));
    return user;
  }

}
