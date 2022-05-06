package io.prizy.publicapi.application.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 13:59
 */

@ConstructorBinding
@ConfigurationProperties(prefix = "prizy.game")
data class GameProperties(
  val id: String,
  val title: String,
  val description: String,
  val usageInstructions: String,
  val iconAssetId: String,
  val coverAssetId: String
)