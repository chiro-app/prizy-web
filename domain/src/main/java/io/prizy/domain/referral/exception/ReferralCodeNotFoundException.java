package io.prizy.domain.referral.exception;

import io.prizy.toolbox.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ErrorCode("REFERRAL_CODE_NOT_FOUND")
public class ReferralCodeNotFoundException extends RuntimeException {
  private final String code;
}
