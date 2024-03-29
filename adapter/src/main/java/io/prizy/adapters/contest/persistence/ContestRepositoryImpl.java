package io.prizy.adapters.contest.persistence;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import io.prizy.adapters.contest.mapper.ContestMapper;
import io.prizy.adapters.contest.merger.ContestEntityMerger;
import io.prizy.adapters.contest.persistence.entity.ContestEntity;
import io.prizy.adapters.contest.persistence.repository.ContestJpaRepository;
import io.prizy.adapters.contest.persistence.repository.PackJpaRepository;
import io.prizy.domain.contest.exception.ContestNotFoundException;
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
  public Optional<Contest> byPackId(UUID packId) {
    return jpaRepository.findByPackId(packId).map(ContestMapper::map);
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
    if (!jpaRepository.existsById(contest.id())) {
      throw new ContestNotFoundException(contest.id());
    }
    var entity = ContestMapper.map(contest);
    entity = ContestEntityMerger.merge(entity, jpaRepository.getById(contest.id()));
    return save(entity);
  }

  @Override
  public Contest create(Contest contest) {
    return save(ContestMapper.map(contest));
  }

  @Override
  public void delete(UUID contestId) {
    jpaRepository.deleteById(contestId);
  }

  @Override
  public Collection<Contest> byIds(Collection<UUID> ids) {
    return jpaRepository.findAllById(ids).stream().map(ContestMapper::map).toList();
  }

  @Override
  public Collection<Contest> endedBetween(Instant from, Instant to) {
    return jpaRepository.findAllByToDateBetween(from, to).stream().map(ContestMapper::map).toList();
  }

  @Override
  public Collection<Contest> startedBeforeAndEndingAfter(Instant instant) {
    return jpaRepository
      .findAllByFromDateLessThanEqualAndToDateGreaterThanEqual(instant, instant)
      .stream()
      .map(ContestMapper::map)
      .toList();
  }

  @Override
  public Collection<Contest> endedBefore(Instant instant) {
    return jpaRepository.findAllByToDateBefore(instant).stream().map(ContestMapper::map).toList();
  }

  @Override
  public Optional<Contest> byAccessCode(String accessCode) {
    return jpaRepository.findByAccessCode(accessCode).map(ContestMapper::map);
  }

  private Contest save(ContestEntity entity) {
    var packEntities = entity.getPacks();
    entity = jpaRepository.save(entity.withPacks(Set.of()));
    final var finalContestEntity = entity;
    packEntities = Set.copyOf(packJpaRepository.saveAll(packEntities
      .stream()
      .peek(packEntity -> packEntity.setContest(finalContestEntity))
      .toList()
    ));
    entity = entity.withPacks(packEntities);
    return ContestMapper.map(entity);
  }
}
