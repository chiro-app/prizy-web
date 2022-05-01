package io.prizy.domain.auth.port;

import java.util.Optional;

import io.prizy.domain.auth.model.ResetToken;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:16 AM
 */


public interface ResetTokenRepository {
  ResetToken save(ResetToken resetToken);

  Optional<ResetToken> byToken(String token);

  void delete(ResetToken resetToken);
}
