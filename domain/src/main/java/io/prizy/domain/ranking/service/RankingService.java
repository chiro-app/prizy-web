package io.prizy.domain.ranking.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

import io.prizy.domain.contest.exception.ContestExpiredException;
import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.ranking.model.RankingRow;
import io.prizy.domain.ranking.model.RankingTable;
import io.prizy.domain.ranking.port.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:12 PM
 */


@Transactional
@RequiredArgsConstructor
public class RankingService {

  private final RankingRepository repository;
  private final ContestRepository contestRepository;

  public RankingTable getForContest(UUID contestId) {
    contestRepository
      .byId(contestId)
      .orElseThrow(() -> new ContestNotFoundException(contestId));
    var rows = repository
      .byContestId(contestId)
      .stream()
      .sorted(Comparator.comparing(RankingRow::score))
      .collect(Collectors.groupingBy(RankingRow::userId))
      .values()
      .stream()
      .map(rankingRows -> rankingRows
        .stream()
        .max(Comparator.comparing(RankingRow::score))
        .get()
      )
      .toList();
    return new RankingTable(contestId, rows);
  }

  public void submitScore(UUID contestId, UUID userId, Long score) {
    var contest = contestRepository
      .byId(contestId)
      .orElseThrow(() -> new ContestNotFoundException(contestId));
    if (contest.toDate().isAfter(Instant.now())) {
      throw new ContestExpiredException();
    }
    repository.save(RankingRow.builder()
      .score(score)
      .contestId(contestId)
      .userId(userId)
      .build()
    );
  }

  public void incrementByAndSubmitScore(UUID contestId, UUID userId, Long score) {
    var maxScoreRow = repository
      .byContestAndUser(contestId, userId)
      .stream().max(Comparator.comparing(RankingRow::score))
      .orElseGet(() -> RankingRow.builder()
        .score(0L)
        .userId(userId)
        .contestId(contestId)
        .dateTime(Instant.now())
        .build()
      );
    repository.save(maxScoreRow.withScore(maxScoreRow.score() + score));
  }

}
