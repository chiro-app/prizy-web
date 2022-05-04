package io.prizy.domain.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.auth.model.Roles;
import io.prizy.domain.user.event.UserCreated;
import io.prizy.domain.user.event.UserUpdated;
import io.prizy.domain.user.exception.UserExistsException;
import io.prizy.domain.user.exception.UserNotFoundException;
import io.prizy.domain.user.mapper.CreateUserMapper;
import io.prizy.domain.user.mapper.UpdateUserMapper;
import io.prizy.domain.user.model.CreateUser;
import io.prizy.domain.user.model.UpdateUser;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.model.UserStatus;
import io.prizy.domain.user.port.PasswordHasher;
import io.prizy.domain.user.port.UserPublisher;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 2:40 PM
 */


@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final PasswordHasher passwordHasher;
  private final UserPublisher userPublisher;

  public User createUser(CreateUser create) {
    var userExists = repository.existsByEmailPhoneOrUsername(
      create.email(),
      create.mobilePhone().orElse(null), create.username()
    );
    if (userExists) {
      throw new UserExistsException();
    }
    var user = repository.save(CreateUserMapper
      .map(create)
      .withRoles(List.of(Roles.USER))
      .withPasswordHash(passwordHasher.hash(create.password()))
    );
    userPublisher.publish(new UserCreated(user));
    return user;
  }

  public User updateUser(UpdateUser update) {
    if (!repository.existsById(update.id())) {
      throw new UserNotFoundException(update.id());
    }
    var user = repository.update(UpdateUserMapper.map(update));
    userPublisher.publish(new UserUpdated(user));
    return user;
  }

  public Optional<User> byId(UUID id) {
    return repository.byId(id);
  }

  public Optional<User> getUserByEmailOrUsername(String emailOrUsername) {
    return repository.byEmailOrUsername(emailOrUsername);
  }

  public Collection<User> listUsers() {
    return repository.list();
  }

  public void confirmUserEmail(UUID id) {
    var user = repository.byId(id).orElseThrow(() -> new UserNotFoundException(id));
    repository.update(user.withStatus(UserStatus.CONFIRMED));
  }

}
