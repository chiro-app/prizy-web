package io.prizy.domain.ranking.service;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.ranking.event.RankingChanged;
import io.prizy.domain.ranking.model.RankingRow;
import io.prizy.domain.ranking.model.RankingTable;
import io.prizy.domain.ranking.port.RankingRepository;
import io.prizy.domain.ranking.publisher.RankingPublisher;
import io.prizy.domain.resources.model.ResourceTransaction;
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

  private final RankingPublisher publisher;
  private final RankingRepository repository;
  private final ContestRepository contestRepository;

  public RankingTable getForContest(UUID contestId) {
    contestRepository.byId(contestId).orElseThrow(() -> new ContestNotFoundException(contestId));
    var rows = repository
      .byContestId(contestId)
      .stream()
      .collect(Collectors.groupingBy(RankingRow::userId))
      .values().stream()
      .map(rankingRows -> rankingRows
        .stream()
        .max(Comparator.comparing(RankingRow::score))
        .get()
      )
      .sorted(Comparator.comparing(RankingRow::score)
        .thenComparing((lhs, rhs) -> (int) (rhs.dateTime().getEpochSecond() - lhs.dateTime().getEpochSecond()))
      )
      .collect(Collectors.collectingAndThen(Collectors.toList(), table -> {
        Collections.reverse(table);
        return table;
      }));
    return new RankingTable(contestId, rows);
  }

  public Optional<RankingRow> ofUser(UUID userId, UUID contestId) {
    return repository
      .byContestAndUser(contestId, userId)
      .stream()
      .max(Comparator.comparingLong(RankingRow::score));
  }

  public void applyTransaction(ResourceTransaction.ContestDependent transaction) {
    var amount = transaction.amount();
    var userId = transaction.userId();
    var contestId = transaction.contestId();

    var previousRanking = repository.rankingOfUserInContest(contestId, userId);

    var row = repository
      .byContestAndUser(contestId, userId)
      .stream()
      .max(Comparator.comparing(RankingRow::score))
      .orElseGet(() -> RankingRow.builder()
        .score(0L)
        .userId(userId)
        .contestId(contestId)
        .dateTime(Instant.now())
        .build()
      );

    var newScore = transaction.type().apply(row.score(), amount);
    log.debug("User's {} score for contest {} is now {}, was {}", userId, contestId, newScore, row.score());
    row = row.withScore(newScore);

    publisher.publish(RankingChanged.builder()
      .userId(userId)
      .contestId(contestId)
      .previousRank(previousRanking)
      .build()
    );

    repository.save(row);
  }

}
