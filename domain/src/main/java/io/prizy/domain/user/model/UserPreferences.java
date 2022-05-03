package io.prizy.domain.user.model;

import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:08 PM
 */


@Builder
public record UserPreferences(
  UUID userId,
  NotificationSettings notifications,
  EmailSettings emails
) {

  public UserPreferences(UUID userId) {
    this(userId, new NotificationSettings(), new EmailSettings());
  }

}
