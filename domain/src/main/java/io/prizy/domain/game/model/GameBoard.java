package io.prizy.domain.game.model;

import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 12:55
 */


@Builder
public record GameBoard(
  UUID id,
  String name,
  Integer[] cells,
  Integer rowSize
) {
}
