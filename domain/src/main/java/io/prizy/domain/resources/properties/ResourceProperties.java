package io.prizy.domain.resources.properties;

import java.util.List;

/**
 * @author Nidhal Dogga
 * @created 4/29/2022 9:34 PM
 */


public record ResourceProperties(
  Integer referrerKeysBonus,
  Integer referralKeysBonus,
  List<Integer> dailyDiamondsBonus,
  List<Integer> dailyLivesBonus,
  Integer dailyKeysBonus,
  Integer livesBoostMultiplier,
  Integer diamondsBoostMultiplier,
  Integer maxBoostReferrals
) {
}
