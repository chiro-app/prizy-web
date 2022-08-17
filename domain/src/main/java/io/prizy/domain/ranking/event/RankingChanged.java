package io.prizy.domain.ranking.event;

import java.util.Optional;
import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 8/8/2022 11:17 PM
 */


@Builder
public record RankingChanged(
  UUID contestId,
  UUID userId,
  Optional<Integer> previousRank
) {
}
