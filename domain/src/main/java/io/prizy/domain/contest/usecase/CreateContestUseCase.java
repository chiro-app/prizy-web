package io.prizy.domain.contest.usecase;

import io.prizy.domain.contest.event.ContestCreated;
import io.prizy.domain.contest.mapper.CreateContestMapper;
import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.CreateContest;
import io.prizy.domain.contest.ports.ContestPublisher;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:34
 */


@Component
@RequiredArgsConstructor
public class CreateContestUseCase {

  private final ContestRepository contestRepository;
  private final ContestPublisher contestPublisher;
  private final ContestService contestService;

  @Transactional
  public Contest create(CreateContest create) {
    var contest = CreateContestMapper
      .map(create)
      .withAccessCode(contestService.generateRandomAccessCode());
    contest = contestRepository.create(contest);
    contestPublisher.publish(new ContestCreated(contest));
    return contest;
  }

}
