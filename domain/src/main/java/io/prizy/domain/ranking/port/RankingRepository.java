package io.prizy.domain.ranking.port;

import java.util.Collection;
import java.util.UUID;

import io.prizy.domain.ranking.model.RankingRow;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:14 PM
 */


public interface RankingRepository {
  RankingRow save(RankingRow row);

  Collection<RankingRow> byContestId(UUID contestId);
}
