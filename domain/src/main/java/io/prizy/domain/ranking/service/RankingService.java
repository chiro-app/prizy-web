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
import io.prizy.domain.resources.model.ResourceTransaction;
import io.prizy.domain.resources.model.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:12 PM
 */


@Slf4j
@Service
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

  public void applyTransaction(ResourceTransaction.ContestDependent transaction) {

    var amount = transaction.amount();
    var userId = transaction.userId();
    var contestId = transaction.contestId();

    var row = repository
      .byContestAndUser(contestId, userId)
      .stream().max(Comparator.comparing(RankingRow::score))
      .orElseGet(() -> RankingRow.builder()
        .score(0L)
        .userId(userId)
        .contestId(contestId)
        .dateTime(Instant.now())
        .build()
      );

    var newScore = transaction.type().apply(row.score(), amount);
    log.info("User's {} score for contest {} is now {}, was {}", userId, contestId, newScore, row.score());
    row = row.withScore(newScore);

    repository.save(row);
  }

}
