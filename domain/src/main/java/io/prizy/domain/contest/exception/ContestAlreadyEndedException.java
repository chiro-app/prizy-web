package io.prizy.domain.contest.exception;

import java.util.UUID;

import io.prizy.toolbox.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ErrorCode("CONTEST_ALREADY_ENDED")
public class ContestAlreadyEndedException extends RuntimeException {
  private final UUID contestId;
}
