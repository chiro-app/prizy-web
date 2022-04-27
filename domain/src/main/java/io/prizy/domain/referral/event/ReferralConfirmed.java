package io.prizy.domain.referral.event;

import java.util.UUID;

public record ReferralConfirmed(
  UUID userId,
  UUID referrerId
) {
}
