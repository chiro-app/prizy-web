package io.prizy.publicapi.application.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 11:20 PM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.resources")
data class ResourceProperties(
  val referrerKeyBonus: Int,
  val referralKeyBonus: Int,
  val dailyDiamondsBonus: List<Int>,
  val dailyLivesBonus: List<Int>,
  val dailyKeysBonus: Int,
  val livesBoostMultiplier: Int,
  val diamondsBoostMultiplier: Int,
  val maxLivesBoost: Int,
  val maxDiamondsBoost: Int
) {

  val toDomain: io.prizy.domain.resources.properties.ResourceProperties
    get() = io.prizy.domain.resources.properties.ResourceProperties(
      referrerKeyBonus,
      referralKeyBonus,
      dailyDiamondsBonus,
      dailyLivesBonus,
      dailyKeysBonus,
      livesBoostMultiplier,
      diamondsBoostMultiplier,
      maxLivesBoost,
      maxDiamondsBoost
    )
}