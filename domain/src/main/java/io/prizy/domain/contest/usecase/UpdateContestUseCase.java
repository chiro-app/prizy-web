package io.prizy.domain.contest.usecase;

import java.time.Instant;

import io.prizy.domain.contest.event.ContestUpdated;
import io.prizy.domain.contest.exception.ContestAlreadyEndedException;
import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.mapper.UpdateContestMapper;
import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.UpdateContest;
import io.prizy.domain.contest.ports.ContestPublisher;
import io.prizy.domain.contest.ports.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:34
 */


@Component
@RequiredArgsConstructor
public class UpdateContestUseCase {

  private final ContestRepository contestRepository;
  private final ContestPublisher contestPublisher;

  @Transactional
  public Contest update(UpdateContest update) {
    if (!contestRepository.exists(update.id())) {
      throw new ContestNotFoundException(update.id());
    }
    var contest = contestRepository.byId(update.id()).get();
    if (contest.toDate().isBefore(Instant.now())) {
      throw new ContestAlreadyEndedException(contest.id());
    }
    contest = contestRepository.update(UpdateContestMapper.map(update));
    contestPublisher.publish(new ContestUpdated(contest));
    return contest;
  }

}
