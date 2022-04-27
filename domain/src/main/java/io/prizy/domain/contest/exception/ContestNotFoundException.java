package io.prizy.domain.contest.exception;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContestNotFoundException extends RuntimeException {
  private final UUID contestId;
}
