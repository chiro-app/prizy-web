package io.prizy.domain.contest.exception;

import java.util.UUID;

import io.prizy.toolbox.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ErrorCode("CONTEST_NOT_FOUND")
public class ContestNotFoundException extends RuntimeException {
  private final UUID contestId;
}
