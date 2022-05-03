package io.prizy.adapters.user.persistence;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.user.mapper.UserPreferencesMapper;
import io.prizy.adapters.user.persistence.repository.UserPreferencesJpaRepository;
import io.prizy.domain.user.model.UserPreferences;
import io.prizy.domain.user.port.UserPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:21 PM
 */


@Component
@RequiredArgsConstructor
public class UserPreferencesRepositoryImpl implements UserPreferencesRepository {

  private final UserPreferencesJpaRepository jpaRepository;

  @Override
  public UserPreferences save(UserPreferences userPreferences) {
    var entity = jpaRepository.save(UserPreferencesMapper.map(userPreferences));
    return UserPreferencesMapper.map(entity);
  }

  @Override
  public Optional<UserPreferences> getForUser(UUID userId) {
    return jpaRepository.findById(userId).map(UserPreferencesMapper::map);
  }

}
