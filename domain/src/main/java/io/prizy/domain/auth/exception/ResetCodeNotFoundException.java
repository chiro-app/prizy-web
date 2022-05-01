package io.prizy.domain.auth.exception;

import io.prizy.toolbox.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:26 AM
 */


@RequiredArgsConstructor
@ErrorCode("RESET_CODE_NOT_FOUND")
public class ResetCodeNotFoundException extends RuntimeException {
  private final String code;
}
