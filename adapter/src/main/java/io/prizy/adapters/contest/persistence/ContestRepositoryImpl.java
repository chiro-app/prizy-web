package io.prizy.adapters.contest.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import io.prizy.adapters.contest.mapper.ContestMapper;
import io.prizy.adapters.contest.persistence.repository.ContestJpaRepository;
import io.prizy.adapters.contest.persistence.repository.PackJpaRepository;
import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.ports.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:12 PM
 */

@Component
@RequiredArgsConstructor
public class ContestRepositoryImpl implements ContestRepository {

  private final ContestJpaRepository jpaRepository;
  private final PackJpaRepository packJpaRepository;

  @Override
  public Optional<Contest> byId(UUID contestId) {
    return jpaRepository
      .findById(contestId)
      .map(ContestMapper::map);
  }

  @Override
  public Boolean exists(UUID contestId) {
    return jpaRepository.existsById(contestId);
  }

  @Override
  public Collection<Contest> list() {
    return jpaRepository
      .findAll()
      .stream().map(ContestMapper::map)
      .toList();
  }

  @Override
  public Contest update(Contest contest) {
    var contestEntity = ContestMapper.map(contest);
    var packEntities = contestEntity.getPacks();
    contestEntity = jpaRepository.save(contestEntity.withPacks(Set.of()));
    final var finalContestEntity = contestEntity;
    packEntities = Set.copyOf(packJpaRepository.saveAll(packEntities
      .stream()
      .peek(packEntity -> packEntity.setContest(finalContestEntity))
      .toList()
    ));
    contestEntity = contestEntity.withPacks(packEntities);
    return ContestMapper.map(contestEntity);
  }

  @Override
  public Contest create(Contest contest) {
    return update(contest);
  }

  @Override
  public void delete(UUID contestId) {
    jpaRepository.deleteById(contestId);
  }
}
