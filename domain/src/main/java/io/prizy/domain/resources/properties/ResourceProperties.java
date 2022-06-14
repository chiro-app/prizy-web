package io.prizy.domain.resources.properties;

/**
 * @author Nidhal Dogga
 * @created 4/29/2022 9:34 PM
 */


public record ResourceProperties(
  Integer referrerKeysBonus,
  Integer referralKeysBonus,
  Integer dailyDiamondsBonus,
  Integer dailyLivesBonus,
  Integer initialDiamondsBonus,
  Integer initialLivesBonus,
  Integer dailyKeysBonus,
  Integer livesBoostMultiplier,
  Integer diamondsBoostMultiplier,
  Integer maxBoostReferrals
) {
}
