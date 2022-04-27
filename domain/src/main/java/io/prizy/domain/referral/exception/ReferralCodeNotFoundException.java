package io.prizy.domain.referral.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReferralCodeNotFoundException extends RuntimeException {
  private final String code;
}
