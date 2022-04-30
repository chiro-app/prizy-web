package io.prizy.domain.user.exception;

import io.prizy.toolbox.exception.ErrorCode;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:12 PM
 */


@ErrorCode("USER_EXISTS")
public class UserExistsException extends RuntimeException {
}
