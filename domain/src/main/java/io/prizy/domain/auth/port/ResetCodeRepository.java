package io.prizy.domain.auth.port;

import java.util.Optional;

import io.prizy.domain.auth.model.ResetCode;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:16 AM
 */


public interface ResetCodeRepository {
  ResetCode save(ResetCode resetCode);

  Optional<ResetCode> byCode(String code);

  void delete(ResetCode resetCode);
}
