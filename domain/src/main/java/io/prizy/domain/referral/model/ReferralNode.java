package io.prizy.domain.referral.model;

import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 2:25 PM
 */

@With
@Builder
public record ReferralNode(
  UUID userId,
  String code,
  Boolean confirmed,
  Optional<UUID> referrerId
) {
}
