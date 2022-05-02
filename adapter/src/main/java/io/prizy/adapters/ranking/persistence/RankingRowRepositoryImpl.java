package io.prizy.adapters.ranking.persistence;

import java.util.Collection;
import java.util.UUID;

import io.prizy.adapters.ranking.mapper.RankingRowMapper;
import io.prizy.adapters.ranking.persistence.repository.RankingRowJpaRepository;
import io.prizy.domain.ranking.model.RankingRow;
import io.prizy.domain.ranking.port.RankingRowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/2/2022 9:37 AM
 */


@Component
@RequiredArgsConstructor
public class RankingRowRepositoryImpl implements RankingRowRepository {

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
      .toList();
  }

}
