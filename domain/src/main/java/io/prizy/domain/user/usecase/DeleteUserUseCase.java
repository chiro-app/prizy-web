package io.prizy.domain.user.usecase;

import java.util.UUID;

import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 22/07/2022 18:47
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {

  private final UserRepository userRepository;

  @Transactional
  public Boolean delete(UUID id) {
    try {
      userRepository.deleteById(id);
      return true;
    } catch (Throwable throwable) {
      log.warn(throwable.getMessage(), throwable);
      return false;
    }
  }

}
