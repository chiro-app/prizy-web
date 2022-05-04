package io.prizy.domain.contest.ports;

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

  Boolean exists(UUID contestId);

  Collection<Contest> list();

  Contest create(Contest contest);

  Contest update(Contest contest);

  void delete(UUID contestId);

  Collection<Contest> byIds(Collection<UUID> ids);
}
