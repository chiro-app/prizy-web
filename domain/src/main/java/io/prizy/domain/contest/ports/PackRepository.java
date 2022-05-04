package io.prizy.domain.contest.ports;

import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.model.Pack;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:02 AM
 */

public interface PackRepository {
  Optional<Pack> byId(UUID id);
}
