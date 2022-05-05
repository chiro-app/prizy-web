package io.prizy.domain.contest.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.event.ContestCreated;
import io.prizy.domain.contest.event.ContestUpdated;
import io.prizy.domain.contest.exception.ContestNotFoundException;
import io.prizy.domain.contest.mapper.CreateContestMapper;
import io.prizy.domain.contest.mapper.UpdateContestMapper;
import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.CreateContest;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.contest.model.UpdateContest;
import io.prizy.domain.contest.ports.ContestPublisher;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.ports.ContestTemplateCompiler;
import io.prizy.domain.contest.ports.PackRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
public class ContestService {

  private static final Integer ACCESS_CODE_LENGTH = 6;
  private static final String ACCESS_CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private final ContestRepository contestRepository;
  private final ContestPublisher contestPublisher;
  private final PackRepository packRepository;
  private final ContestTemplateCompiler templateCompiler;

  public Optional<Contest> byId(UUID id) {
    return contestRepository.byId(id);
  }

  public Optional<Contest> byPackId(UUID packId) {
    return contestRepository.byPackId(packId);
  }

  public Collection<Contest> byIds(Collection<UUID> ids) {
    return contestRepository.byIds(ids);
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

  public Contest createContest(CreateContest create) {
    var contest = CreateContestMapper
      .map(create)
      .withAccessCode(RandomStringUtils.random(ACCESS_CODE_LENGTH, ACCESS_CODE_ALPHABET));
    contest = contestRepository.create(contest);
    contestPublisher.publish(new ContestCreated(contest));
    return contest;
  }

  public Contest updateContest(UpdateContest update) {
    if (!contestRepository.exists(update.id())) {
      throw new ContestNotFoundException(update.id());
    }
    var contest = contestRepository.update(UpdateContestMapper.map(update));
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
