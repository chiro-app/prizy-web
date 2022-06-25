package io.prizy.publicapi.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 11:20 PM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.resources")
public record ResourceProperties(
  Integer referrerKeyBonus,
  Integer referralKeyBonus,
  Integer dailyDiamondsBonus,
  Integer dailyLivesBonus,
  Integer initialDiamondsBonus,
  Integer initialLivesBonus,
  Integer dailyKeysBonus,
  Integer livesBoostMultiplier,
  Integer diamondsBoostMultiplier,
  Integer maxBoostReferrals
) {

  public io.prizy.domain.resources.properties.ResourceProperties getDomain() {
    return new io.prizy.domain.resources.properties.ResourceProperties(
      referrerKeyBonus,
      referralKeyBonus,
      dailyDiamondsBonus,
      dailyLivesBonus,
      initialDiamondsBonus,
      initialLivesBonus,
      dailyKeysBonus,
      livesBoostMultiplier,
      diamondsBoostMultiplier,
      maxBoostReferrals
    );
  }

}