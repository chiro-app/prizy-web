package io.prizy.adapters.reward.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.reward.mapper.RewardMapper;
import io.prizy.adapters.reward.persistence.repository.RewardJpaRepository;
import io.prizy.domain.reward.domain.Reward;
import io.prizy.domain.reward.port.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 04/05/2022 14:56
 */


@Component
@RequiredArgsConstructor
public class RewardRepositoryImpl implements RewardRepository {

  private final RewardJpaRepository jpaRepository;

  @Override
  public Collection<Reward> save(Collection<Reward> rewards) {
    return jpaRepository
      .saveAll(rewards.stream().map(RewardMapper::map).toList())
      .stream().map(RewardMapper::map)
      .toList();
  }

  @Override
  public Optional<Reward> lastCreated() {
    return jpaRepository.findTop1ByOrderByCreatedDesc().map(RewardMapper::map);
  }

  @Override
  public Collection<Reward> byUserId(UUID userId) {
    return jpaRepository.findAllByUserId(userId).stream().map(RewardMapper::map).toList();
  }

  @Override
  public Collection<Reward> byContestId(UUID contestId) {
    return jpaRepository.findAllByContestId(contestId).stream().map(RewardMapper::map).toList();
  }

}
