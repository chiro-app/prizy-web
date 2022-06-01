package io.prizy.adapters.ranking.mapper;

import io.prizy.adapters.ranking.persistence.entity.RankingRowEntity;
import io.prizy.domain.ranking.model.RankingRow;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/2/2022 9:42 AM
 */


@UtilityClass
public class RankingRowMapper {

  public RankingRow map(RankingRowEntity entity) {
    return RankingRow.builder()
      .id(entity.getId())
      .score(entity.getScore())
      .contestId(entity.getContestId())
      .userId(entity.getUserId())
      .dateTime(entity.getDateTime())
      .build();
  }

  public RankingRowEntity map(RankingRow row) {
    return RankingRowEntity.builder()
      .id(row.id())
      .score(row.score())
      .contestId(row.contestId())
      .userId(row.userId())
      .dateTime(row.dateTime())
      .build();
  }

}
