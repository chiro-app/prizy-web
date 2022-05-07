package io.prizy.domain.game.model;

import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.user.model.User;
import lombok.Builder;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 2:05 PM
 */


@With
@Builder
public record GameContext(
  User user,
  Contest contest,
  GameSession session,
  Long initialDiamondsBet
) {
}
