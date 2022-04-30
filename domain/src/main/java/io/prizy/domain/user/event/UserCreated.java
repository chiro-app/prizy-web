package io.prizy.domain.user.event;

import io.prizy.domain.user.model.User;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:21 PM
 */


public record UserCreated(
  User user
) {
}
