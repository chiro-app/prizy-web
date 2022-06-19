package io.prizy.publicapi.application.properties

import io.prizy.domain.game.properties.GameProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 *  @author Nidhal Dogga
 *  @created 5/7/2022 3:59 PM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.game")
data class GameProperties(
  val obstacles: Collection<ObstacleRange>
) {

  data class ObstacleRange(
    val from: Long?,
    val to: Long?,
    val obstacles: Int
  )

  val toDomain: GameProperties
    get() = GameProperties(
      obstacles
        .map { o ->
          GameProperties.ObstacleRange(
            o.from ?: 0L,
            o.to ?: Long.MAX_VALUE,
            o.obstacles
          )
        }
    )
}
