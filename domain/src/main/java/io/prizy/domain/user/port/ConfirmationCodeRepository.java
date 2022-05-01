package io.prizy.domain.user.port;

import java.util.Optional;

import io.prizy.domain.user.model.ConfirmationCode;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 12:40 PM
 */

public interface ConfirmationCodeRepository {
  Optional<ConfirmationCode> byCode(String code);

  ConfirmationCode save(ConfirmationCode confirmationCode);

  void deleteByCode(String code);
}