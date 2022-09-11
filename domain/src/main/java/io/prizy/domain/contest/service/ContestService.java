package io.prizy.domain.contest.service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.ports.ContestTemplateCompiler;
import io.prizy.domain.contest.ports.PackRepository;
import io.prizy.toolbox.useragent.UserAgent;
import io.prizy.toolbox.useragent.UserAgentParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ContestService {

  private static final Integer ACCESS_CODE_LENGTH = 6;
  private static final String ACCESS_CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private final ContestRepository contestRepository;
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

  public Collection<Contest> activeContests() {
    return contestRepository.startedBeforeAndEndingAfter(Instant.now());
  }

  public Collection<Contest> listAllContests() {
    return contestRepository.list();
  }

  public String generateRandomAccessCode() {
    return RandomStringUtils.random(ACCESS_CODE_LENGTH, ACCESS_CODE_ALPHABET);
  }

  public Optional<Pack> packById(UUID id) {
    return packRepository.byId(id);
  }

  public String contestRules(UUID contestId, String userAgent) {
    var contest = contestRepository
      .byId(contestId)
      .orElseThrow(() -> new IllegalArgumentException("Contest not found"));
    var platform = UserAgentParser.parse(userAgent).map(UserAgent::platform).orElse("Unknown");
    return templateCompiler.rulesTemplate(contest, platform);
  }

}
