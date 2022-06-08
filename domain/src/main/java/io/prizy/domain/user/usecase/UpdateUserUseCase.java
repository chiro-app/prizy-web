package io.prizy.domain.user.usecase;

import io.prizy.domain.user.event.UserUpdated;
import io.prizy.domain.user.exception.UserNotFoundException;
import io.prizy.domain.user.mapper.UpdateUserMapper;
import io.prizy.domain.user.model.UpdateUser;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.port.UserPublisher;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 20:00
 */


@Component
@RequiredArgsConstructor
public class UpdateUserUseCase {

  private final UserRepository userRepository;
  private final UserPublisher userPublisher;

  @Transactional
  public User update(UpdateUser update) {
    if (!userRepository.existsById(update.id())) {
      throw new UserNotFoundException(update.id());
    }
    var user = userRepository.update(UpdateUserMapper.map(update));
    userPublisher.publish(new UserUpdated(user));
    return user;
  }

}
