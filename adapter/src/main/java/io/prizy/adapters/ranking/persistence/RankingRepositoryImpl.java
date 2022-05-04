package io.prizy.adapters.ranking.persistence;

import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;

import io.prizy.adapters.ranking.mapper.RankingRowMapper;
import io.prizy.adapters.ranking.persistence.repository.RankingRowJpaRepository;
import io.prizy.domain.ranking.model.RankingRow;
import io.prizy.domain.ranking.port.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/2/2022 9:37 AM
 */


@Component
@RequiredArgsConstructor
public class RankingRepositoryImpl implements RankingRepository {

  private final RankingRowJpaRepository jpaRepository;

  @Override
  public RankingRow save(RankingRow row) {
    var entity = jpaRepository.save(RankingRowMapper.map(row));
    return RankingRowMapper.map(entity);
  }

  @Override
  public Collection<RankingRow> byContestId(UUID contestId) {
    return jpaRepository
      .findAllByContestId(contestId)
      .stream()
      .map(RankingRowMapper::map)
      .sorted(Comparator.comparingLong(RankingRow::score))
      .toList();
  }

}
