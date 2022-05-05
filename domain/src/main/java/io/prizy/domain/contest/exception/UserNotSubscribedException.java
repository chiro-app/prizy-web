package io.prizy.domain.contest.exception;

import java.util.UUID;

import io.prizy.toolbox.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 13:11
 */


@RequiredArgsConstructor
@ErrorCode("USER_NOT_SUBSCRIBED")
public class UserNotSubscribedException extends RuntimeException {
  private final UUID userId;
  private final UUID contestId;
}
