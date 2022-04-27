package io.prizy.domain.referral.ports;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.referral.model.ReferralNode;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:55 AM
 */

public interface ReferralRepository {
  Collection<ReferralNode> byReferrerId(UUID userId);
  Optional<ReferralNode> byReferralCode(String referralCode);
  Optional<ReferralNode> referrer(UUID id);
  Optional<ReferralNode> byUserId(UUID userId);
  ReferralNode save(ReferralNode node);
}
