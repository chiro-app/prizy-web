package io.prizy.domain.referral.event;

import java.util.UUID;

public record ReferralCreated(
  UUID userId,
  UUID referrerId
) {
}
