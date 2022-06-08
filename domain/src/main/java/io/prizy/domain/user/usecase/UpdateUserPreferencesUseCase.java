package io.prizy.domain.user.usecase;

import io.prizy.domain.user.model.UserPreferences;
import io.prizy.domain.user.port.UserPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:58
 */

@Component
@RequiredArgsConstructor
public class UpdateUserPreferencesUseCase {

  private final UserPreferencesRepository repository;

  @Transactional
  public UserPreferences update(UserPreferences preferences) {
    return repository.save(preferences);
  }

}
