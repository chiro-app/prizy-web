package io.prizy.domain.ranking.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:11 PM
 */


@Builder
public record RankingRow(
  UUID userId,
  UUID contestId,
  Long score,
  Instant dateTime
) {
}
