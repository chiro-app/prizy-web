package io.prizy.domain.ranking.event;

import io.prizy.domain.ranking.model.RankingRow;
import lombok.Builder;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 8/8/2022 11:17 PM
 */


@Builder
public record RankingChanged(
  UUID contestId,
  RankingRow newEntry,
  Optional<RankingRow> oldEntry
) {
}
