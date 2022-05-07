package io.prizy.domain.user.service;

import java.util.UUID;

import io.prizy.domain.user.model.UserPreferences;
import io.prizy.domain.user.port.UserPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:11 PM
 */


@Service
@RequiredArgsConstructor
public class UserPreferencesService {

  private final UserPreferencesRepository repository;

  public UserPreferences forUser(UUID userId) {
    return repository
      .getForUser(userId)
      .orElseGet(() -> repository.save(new UserPreferences(userId)));
  }

  public UserPreferences update(UserPreferences preferences) {
    return repository.save(preferences);
  }

}
