package io.prizy.domain.resources.ports;

import java.util.UUID;

public interface ResourceRepository {
  Long countKeys(UUID userId);
}