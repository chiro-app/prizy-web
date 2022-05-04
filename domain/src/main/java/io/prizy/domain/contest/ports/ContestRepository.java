package io.prizy.domain.contest.ports;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.model.Contest;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 12:20 AM
 */

public interface ContestRepository {
  Optional<Contest> byId(UUID contestId);

  Optional<Contest> byPackId(UUID packId);

  Boolean exists(UUID contestId);

  Collection<Contest> list();

  Contest create(Contest contest);

  Contest update(Contest contest);

  void delete(UUID contestId);

  Collection<Contest> byIds(Collection<UUID> ids);

  Collection<Contest> endedBetween(Instant from, Instant to);

  Collection<Contest> endedBefore(Instant instant);

  Optional<Contest> byAccessCode(String accessCode);
}
