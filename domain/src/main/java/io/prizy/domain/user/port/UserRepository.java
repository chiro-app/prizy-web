package io.prizy.domain.user.port;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.user.model.User;
import org.springframework.lang.Nullable;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:04 PM
 */


public interface UserRepository {
  User save(User user);

  User update(User user);

  Optional<User> byId(UUID id);

  Boolean existsById(UUID id);

  Boolean existsByEmailPhoneOrUsername(String email, @Nullable String username, String phone);

  Collection<User> list();
}
