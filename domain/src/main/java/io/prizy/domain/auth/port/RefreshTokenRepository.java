package io.prizy.domain.auth.port;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 11:07 PM
 */


public interface RefreshTokenRepository {
  Optional<String> getForUser(UUID userId);
  String save(UUID userId, String token);
  Optional<UUID> findUserId(String token);
}
