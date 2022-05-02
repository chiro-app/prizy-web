package io.prizy.domain.ranking.model;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:10 PM
 */


public record RankingTable(
  UUID contestId,
  Collection<RankingRow> rows
) {
}
