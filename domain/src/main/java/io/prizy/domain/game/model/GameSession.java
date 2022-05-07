package io.prizy.domain.game.model;

import java.util.Collection;

import lombok.Builder;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 2:10 PM
 */


@With
@Builder
public record GameSession(
  Long score,
  GameBoard board,
  Integer endPosition,
  Integer startPosition,
  Integer currentPosition,
  Collection<Integer> obstacles
) {
}
