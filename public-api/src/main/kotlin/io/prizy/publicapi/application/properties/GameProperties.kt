package io.prizy.publicapi.application.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 *  @author Nidhal Dogga
 *  @created 5/7/2022 3:59 PM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.game")
data class GameProperties(
  val maxRandomObstacles: Int,
  val minRandomObstacles: Int
) {

  val toDomain: io.prizy.domain.game.properties.GameProperties
    get() = io.prizy.domain.game.properties.GameProperties(
      maxRandomObstacles,
      minRandomObstacles
    )
}
