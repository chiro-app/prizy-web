package io.prizy.domain.reward.service;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

import io.prizy.domain.contest.exception.InvalidContestAccessCodeException;
import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.ranking.port.RankingRepository;
import io.prizy.domain.reward.domain.Reward;
import io.prizy.domain.reward.event.RewardCreated;
import io.prizy.domain.reward.port.RewardPublisher;
import io.prizy.domain.reward.port.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 7:16 PM
 */


@Service
@Transactional
@RequiredArgsConstructor
public class RewardService {

  private final RewardPublisher publisher;
  private final RewardRepository repository;
  private final ContestRepository contestRepository;
  private final RankingRepository rankingRepository;

  public void affectRewardsToFinishedContests() {
    var rewards = repository.lastCreated()
      .map(reward -> contestRepository.endedBetween(reward.created(), Instant.now()))
      .orElseGet(() -> contestRepository.endedBefore(Instant.now()))
      .stream()
      .flatMap(contest -> affectRewards(contest).stream())
      .toList();
    repository.save(rewards).forEach(reward -> publisher.publish(new RewardCreated(reward)));
  }

  public Collection<Reward> userRewards(UUID userId) {
    return repository.byUserId(userId);
  }

  public Collection<Reward> contestRewardsByAccessCode(String contestAccessCode) {
    var contest = contestRepository
      .byAccessCode(contestAccessCode)
      .orElseThrow(InvalidContestAccessCodeException::new);
    return repository.byContestId(contest.id());
  }

  Collection<Reward> affectRewards(Contest contest) {
    var rows = rankingRepository.byContestId(contest.id());
    return contest.packs()
      .stream()
      .flatMap(pack -> rows
        .stream()
        .skip(pack.firstWinnerPosition() - 1)
        .limit(pack.lastWinnerPosition() - pack.firstWinnerPosition())
        .map(row -> Reward.builder()
          .userId(row.userId())
          .packId(pack.id())
          .created(Instant.now())
          .build()
        )
      )
      .toList();
  }

}
