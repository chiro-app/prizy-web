package io.prizy.domain.game.properties;

import java.util.Collection;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 3:14 PM
 */


public record GameProperties(
  Collection<ObstacleRange> obstacles
) {

  public record ObstacleRange(
    Long from,
    Long to,
    Integer obstacles
  ) {
  }

}
