package io.prizy.publicapi.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 3:59 PM
 */


@ConstructorBinding
@ConfigurationProperties("prizy.game")
public record GameProperties(
  Collection<ObstacleRange> obstacles
) {

  public io.prizy.domain.game.properties.GameProperties getDomain() {
    return new io.prizy.domain.game.properties.GameProperties(
      obstacles
        .stream()
        .map(obstacle ->
          new io.prizy.domain.game.properties.GameProperties.ObstacleRange(
            Optional.ofNullable(obstacle.from).orElse(0L),
            Optional.ofNullable(obstacle.to).orElse(Long.MAX_VALUE),
            obstacle.obstacles
          )
        )
        .toList()
    );
  }

  public record ObstacleRange(
    Long from,
    Long to,
    Integer obstacles
  ) {

  }

}