package io.prizy.adapters.user.mapper;

import io.prizy.adapters.user.persistence.entity.UserPreferencesEntity;
import io.prizy.domain.user.model.UserPreferences;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:27 PM
 */


@UtilityClass
public class UserPreferencesMapper {

  public UserPreferences map(UserPreferencesEntity entity) {
    return UserPreferences.builder()
      .userId(entity.getUserId())
      .emails(entity.getEmails())
      .notifications(entity.getNotifications())
      .build();
  }

  public UserPreferencesEntity map(UserPreferences preferences) {
    return UserPreferencesEntity.builder()
      .userId(preferences.userId())
      .emails(preferences.emails())
      .notifications(preferences.notifications())
      .build();
  }

}
