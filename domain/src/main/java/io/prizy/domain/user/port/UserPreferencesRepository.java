package io.prizy.domain.user.port;

import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.user.model.UserPreferences;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:12 PM
 */


public interface UserPreferencesRepository {
  UserPreferences save(UserPreferences userPreferences);

  Optional<UserPreferences> getForUser(UUID userId);
}
