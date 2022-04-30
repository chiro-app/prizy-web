package io.prizy.domain.user.exception;

import java.util.UUID;

import io.prizy.toolbox.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:06 PM
 */


@RequiredArgsConstructor
@ErrorCode("USER_NOT_FOUND")
public class UserNotFoundException extends RuntimeException {
  private final UUID userId;
}
