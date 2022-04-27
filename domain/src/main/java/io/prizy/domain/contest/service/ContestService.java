package io.prizy.domain.contest.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.command.CreateContest;
import io.prizy.domain.contest.command.UpdateContest;
import io.prizy.domain.contest.event.ContestCreated;
import io.prizy.domain.contest.event.ContestUpdated;
import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.mapper.CreateContestMapper;
import io.prizy.domain.contest.mapper.UpdateContestMapper;
import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.contest.ports.ContestPublisher;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.ports.ContestTemplateCompiler;
import io.prizy.domain.contest.ports.PackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
public class ContestService {

  private final ContestRepository contestRepository;
  private final ContestPublisher contestPublisher;
  private final PackRepository packRepository;
  private final ContestTemplateCompiler templateCompiler;

  public Optional<Contest> contestById(UUID id) {
    return contestRepository.byId(id);
  }

  public Collection<Contest> contests() {
    return contestRepository.list();
  }

  public Collection<Contest> userContests(UUID userId) {
    return contestRepository.list();
  }

  public Collection<Contest> listAllContests() {
    return contestRepository.list();
  }

  public Contest createContest(CreateContest command) {
    var contest = contestRepository.create(CreateContestMapper.map(command));
    contestPublisher.publish(new ContestCreated(contest));
    return contest;
  }

  public Contest updateContest(UpdateContest command) {
    if (!contestRepository.exists(command.id())) {
      throw new ContestNotFoundException(command.id());
    }
    var contest = contestRepository.update(UpdateContestMapper.map(command));
    contestPublisher.publish(new ContestUpdated(contest));
    return contest;
  }

  public Optional<Pack> packById(UUID id) {
    return packRepository.byId(id);
  }

  public String contestRules(UUID contestId) {
    var contest = contestRepository
      .byId(contestId)
      .orElseThrow(() -> new IllegalArgumentException("Contest not found"));
    return templateCompiler.rulesTemplate(contest);
  }

}
