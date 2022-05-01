package io.prizy.domain.auth.exception;

import io.prizy.toolbox.exception.ErrorCode;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:26 AM
 */


@ErrorCode("RESET_CODE_EXPIRED")
public class ResetCodeExpiredException extends RuntimeException {
}
